package presentation

import NewsItemDomain
import NewsListDomain
import core.DispatchersList
import data.*
import data.models.NewsItemData
import data.models.NewsListData
import domain.*
import kotlinx.coroutines.delay

/**
 * @author Demitrist on 11.02.2023
 **/

private val url = "https://api2.kiparo.com/static/it_news.json"

fun main() {
    val m = Main()
    m.init()
    while (true){

    }
}


fun showResult(str: String) {
    println(str)
}

class Main() {
    private lateinit var repository: NewsRepository
    private lateinit var service: CloudService
    private lateinit var cloudDataSource: DataSource
    private lateinit var cacheDataSource: DataSource.Mutable
    private lateinit var resultDataMapper: ResultDataToDomainMapper
    private lateinit var newsListDataMapper: NewsListData.Mapper<NewsListDomain>
    private lateinit var newsItemDataMapper: NewsItemData.Mapper<NewsItemDomain>
    private lateinit var dispatchers: DispatchersList
    private lateinit var usualNewsUseCase: UsualNewsUseCase
    private lateinit var specialNewsUseCase: SpecialNewsUseCase
    private lateinit var keywordsUseCase: KeywordsUseCase
    private lateinit var resultDomainMapper: ResultDomainToUiMapper
    private lateinit var newsListDomainMapper: NewsListDomain.Mapper<String>
    private lateinit var newsItemDomainMapper: NewsItemDomain.Mapper<String>
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
    private lateinit var formatter: Formatter<String, String>
    private lateinit var usualMenuFactory: MenuFactory.Usual
    private lateinit var keywordsMenuFactory: MenuFactory.Keywords

    fun init() {
        service = CloudService.OkHttpService(responseUrl = URL)
        cloudDataSource = CloudDataSource(service = service)
        cacheDataSource = CacheDataSource()
        newsItemDataMapper = NewsItemData.Mapper.ToDomain()
        newsListDataMapper = NewsListData.Mapper.ToDomain(itemMapper = newsItemDataMapper)
        resultDataMapper = ResultDataToDomainMapper.Base(newsMapper = newsListDataMapper)
        repository = BaseNewsRepository(cloud = cloudDataSource, cache = cacheDataSource, mapper = resultDataMapper)

        var output: (String) -> Unit = { defaultOutput(it) }
        dispatchers = DispatchersList.Base()
        newsItemDomainMapper = NewsItemDomain.Mapper.ToString()
        newsListDomainMapper = NewsListDomain.Mapper.ToString(itemMapper = newsItemDomainMapper)
        formatter = Formatter.StringFormatter(maxLength = 80)
        resultDomainMapper = ResultDomainToUiMapper.Base(listMapper = newsListDomainMapper, formatter = formatter)
        usualNewsUseCase = UsualNewsUseCase.Base(
            repository = repository,
            dispatchers = dispatchers,
            uiMapper = resultDomainMapper,
            output = output
        )
        specialNewsUseCase = SpecialNewsUseCase.Base(
            repository = repository,
            dispatchers = dispatchers,
            uiMapper = resultDomainMapper,
            output = output
        )
        keywordsUseCase = KeywordsUseCase.Base(
            repository = repository,
            dispatchers = dispatchers
        )
        parserInput = InputParser()
        handlerUsersInput = UserInput(parser = parserInput)
        allNewsCommand = MenuCommand.AllNews(useCase = usualNewsUseCase)
        specialNewsCommand = MenuCommand.SpecialNews(useCase = specialNewsUseCase)
        addKeywordCommand = MenuCommand.AddKeyword(useCase = specialNewsUseCase)
        setSortOrderCommand = MenuCommand.SortOrder(useCase = specialNewsUseCase)

        exitCommand = MenuCommand.Exit()
        usualMenuFactory = MenuFactory.Base(handler = handlerUsersInput)
        usualMenuFactory.putItem(MenuItem.Base(text = "1. Sort by date with asc.order", command = setSortOrderCommand))
        usualMenuFactory.putItem(MenuItem.Base(text = "2. Sort by date with desc.order", command = setSortOrderCommand))
        sortOrderMenu = usualMenuFactory.menu(name = "Sort order")
        usualMenuFactory.clear()
        menuSortOrderCommand = MenuCommand.MenuSortOrder(menu = sortOrderMenu)

        keywordsMenuFactory =
            MenuFactory.KeywordsMenuFactory(handler = handlerUsersInput, addKeywordCommand = addKeywordCommand)
        menuKeywordCommand = MenuCommand.MenuKeywords(useCase = keywordsUseCase, menuFactory = keywordsMenuFactory)

        usualMenuFactory.putItem(MenuItem.Base(text = "1. Show all news", command = allNewsCommand))
        usualMenuFactory.putItem(MenuItem.Base(text = "2. Show special news", command = specialNewsCommand))
        usualMenuFactory.putItem(MenuItem.Base(text = "3. Set sort order", command = menuSortOrderCommand))
        usualMenuFactory.putItem(MenuItem.Base(text = "4. Keywords", command = menuKeywordCommand))
        usualMenuFactory.putItem(MenuItem.Base(text = "5. Exit", command = exitCommand))
        mainMenu = usualMenuFactory.menu("Main menu: ")
        usualMenuFactory.clear()
        mainMenu.showForResult()
    }

    fun defaultOutput(text: String) {
        println(text)
        mainMenu.showForResult()
    }

    companion object {
        const val URL = "https://api2.kiparo.com/static/it_news.json"
    }
}