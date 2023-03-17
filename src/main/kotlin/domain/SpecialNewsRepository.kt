package domain

import data.KeyStore
import data.SortOrderStore

/**
 * @author Demitrist on 10.03.2023
 **/

interface SpecialNewsRepository : NewsRepository, KeyStore, SortOrderStore