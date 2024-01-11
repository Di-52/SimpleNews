package presentation

import core.DispatchersList
import core.DispatchersListImpl
import data.CacheDataSource
import data.CloudDataSource
import data.CloudService
import data.DataSource
import data.MutableDataSource
import data.OkHttpService
import data.ResultDataToDomainMapper
import data.SpecialNewsRepositoryImpl
import data.UsualNewsRepository
import data.models.NewsItemDataToDomainMapper
import data.models.NewsListDataToDomainMapper
import domain.FetchKeywordsUseCase
import domain.FetchKeywordsUseCaseImpl
import domain.FetchNewsUseCase
import domain.FetchNewsUseCaseImpl
import domain.NewsRepository
import domain.SetKeywordUseCaseImpl
import domain.SetSortOrderUseCaseImpl
import domain.SpecialNewsRepository
import domain.mappers.NewsItemDomainMapper
import domain.mappers.NewsItemDomainToStringMapper
import domain.mappers.NewsListDomainMapper
import domain.mappers.NewsListToStringMapper
import domain.mappers.ResultDomainMapper
import domain.mappers.ResultDomainToUiMapper
import presentation.menu.AddKeywordMenuCommand
import presentation.menu.AllNewsMenuCommand
import presentation.menu.ChangeSortOrderMenuCommand
import presentation.menu.ExitMenuCommand
import presentation.menu.KeywordsMenuCommand
import presentation.menu.KeywordsMenuFactory
import presentation.menu.KeywordsMenuFactoryImpl
import presentation.menu.Menu
import presentation.menu.MenuCommand
import presentation.menu.MenuItemImpl
import presentation.menu.SortOrderMenuCommand
import presentation.menu.SpecialNewsMenuCommand
import presentation.menu.UsualMenuFactory
import presentation.menu.UsualMenuFactoryImpl

/**
 * @author Demitrist on 11.02.2023
 **/

fun main() {
    val m = Main()
    m.init()
    while (true) {

    }
}

class Main() {
    private lateinit var repository: NewsRepository
    private lateinit var specialRepository: SpecialNewsRepository
    private lateinit var service: CloudService
    private lateinit var cloudDataSource: DataSource
    private lateinit var cacheDataSource: MutableDataSource
    private lateinit var resultDataMapper: ResultDataToDomainMapper
    private lateinit var newsListDataMapper: NewsListDataToDomainMapper
    private lateinit var newsItemDataMapper: NewsItemDataToDomainMapper
    private lateinit var dispatchers: DispatchersList
    private lateinit var usualNewsUseCase: FetchNewsUseCase
    private lateinit var specialNewsUseCase: FetchNewsUseCase
    private lateinit var keywordsUseCase: FetchKeywordsUseCase
    private lateinit var resultDomainMapper: ResultDomainMapper
    private lateinit var newsListDomainMapper: NewsListDomainMapper<String>
    private lateinit var newsItemDomainMapper: NewsItemDomainMapper<String>
    private lateinit var allNewsCommand: MenuCommand
    private lateinit var specialNewsCommand: MenuCommand
    private lateinit var addKeywordCommand: MenuCommand
    private lateinit var setSortOrderCommand: MenuCommand
    private lateinit var menuKeywordCommand: MenuCommand
    private lateinit var menuSortOrderCommand: MenuCommand
    private lateinit var exitCommand: MenuCommand
    private lateinit var handlerUsersInput: UserInput
    private lateinit var parserInput: InputParser
    private lateinit var sortOrderMenu: Menu
    private lateinit var mainMenu: Menu
    private lateinit var formatter: StringFormatter
    private lateinit var usualMenuFactory: UsualMenuFactory
    private lateinit var keywordsMenuFactory: KeywordsMenuFactory

    fun init() {
        service = OkHttpService(responseUrl = URL)
        cloudDataSource = CloudDataSource(service = service)
        cacheDataSource = CacheDataSource()
        newsItemDataMapper = NewsItemDataToDomainMapper()
        newsListDataMapper = NewsListDataToDomainMapper(itemMapper = newsItemDataMapper)
        resultDataMapper = ResultDataToDomainMapper(newsMapper = newsListDataMapper)
        repository = UsualNewsRepository(
            cloud = cloudDataSource,
            cache = cacheDataSource,
            resultMapper = resultDataMapper
        )

        val output: (String) -> Unit = { defaultOutput(it) }
        dispatchers = DispatchersListImpl()
        newsItemDomainMapper = NewsItemDomainToStringMapper()
        newsListDomainMapper = NewsListToStringMapper(itemMapper = newsItemDomainMapper)
        formatter = StringFormatter(maxLength = 80)
        resultDomainMapper =
            ResultDomainToUiMapper(listMapper = newsListDomainMapper, formatter = formatter)
        usualNewsUseCase = FetchNewsUseCaseImpl(
            repository = repository,
            dispatchers = dispatchers,
            uiMapper = resultDomainMapper,
            output = output
        )
        specialRepository = SpecialNewsRepositoryImpl(
            repository = repository
        )
        specialNewsUseCase = FetchNewsUseCaseImpl(
            repository = specialRepository,
            dispatchers = dispatchers,
            uiMapper = resultDomainMapper,
            output = output
        )
        keywordsUseCase = FetchKeywordsUseCaseImpl(
            repository = repository,
            dispatchers = dispatchers
        )
        val setKeywordsUseCase = SetKeywordUseCaseImpl(
            repository = specialRepository,
            callback = output
        )
        val setSortOrderUseCase = SetSortOrderUseCaseImpl(
            repository = specialRepository,
            callback = output
        )

        parserInput = InputParser()
        handlerUsersInput = UsersInputHandler(parser = parserInput)
        allNewsCommand = AllNewsMenuCommand(useCase = usualNewsUseCase)
        specialNewsCommand = SpecialNewsMenuCommand(useCase = specialNewsUseCase)
        addKeywordCommand = AddKeywordMenuCommand(useCase = setKeywordsUseCase)
        setSortOrderCommand = ChangeSortOrderMenuCommand(useCase = setSortOrderUseCase)

        exitCommand = ExitMenuCommand()
        usualMenuFactory = UsualMenuFactoryImpl(handler = handlerUsersInput)
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "1. Sort by date with asc.order",
                command = setSortOrderCommand
            )
        )
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "2. Sort by date with desc.order",
                command = setSortOrderCommand
            )
        )
        sortOrderMenu = usualMenuFactory.menu(name = "Sort order")
        usualMenuFactory.clear()
        menuSortOrderCommand = SortOrderMenuCommand(menu = sortOrderMenu)
        keywordsMenuFactory = KeywordsMenuFactoryImpl(
            handler = handlerUsersInput,
            addKeywordCommand = addKeywordCommand
        )
        menuKeywordCommand = KeywordsMenuCommand(
            useCase = keywordsUseCase,
            menuFactory = keywordsMenuFactory
        )
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "1. Show all news",
                command = allNewsCommand
            )
        )
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "2. Show special news",
                command = specialNewsCommand
            )
        )
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "3. Set sort order",
                command = menuSortOrderCommand
            )
        )
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "4. Keywords",
                command = menuKeywordCommand
            )
        )
        usualMenuFactory.putItem(
            MenuItemImpl(
                text = "5. Exit",
                command = exitCommand
            )
        )
        mainMenu = usualMenuFactory.menu(name = "Main menu: ")
        usualMenuFactory.clear()
        mainMenu.showForResult()
    }

    private fun defaultOutput(text: String) {
        println(text)
        mainMenu.showForResult()
    }

    companion object {
        const val URL = "https://api2.kiparo.com/static/it_news.json"
    }
}