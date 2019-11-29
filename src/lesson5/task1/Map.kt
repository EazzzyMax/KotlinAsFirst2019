@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val res = mutableMapOf<Int, List<String>>()

    for ((name, grade) in grades) {
        res[grade] = res.getOrDefault(grade, listOf()) + name
    }
    return res
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key) in a) {
        if (a[key] != b[key]) return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((key) in b) {
        if (a[key] == b[key]) {
            a.remove(key)
        }
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val answer = mutableSetOf<String>()
    for (name in a) {
        if (name in b) {
            answer += name
        }
    }
    return answer.toList()
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val newMap = mutableMapOf<String, String>()

    for ((name) in mapA) {
        newMap[name] = mapA[name]!!
    }
    for ((name) in mapB) {
        if (name !in newMap) {
            newMap[name] = mapB[name]!!
        } else if (mapB[name] != mapA[name]) {
            newMap[name] = newMap[name] + ", " + mapB[name]
        }
    }
    return newMap
}


/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val first = mutableMapOf<String, List<Double>>() //все цены
    val second = mutableMapOf<String, Double>() //усредненные
    for ((first1, second1) in stockPrices) {
        first[first1] = first.getOrDefault(first1, listOf()) + second1
    }
    for ((key) in first) {
        second[key] = first[key]!!.sum() / first[key]!!.size
    }
    return second
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minPrice = Double.MAX_VALUE
    var ansName: String?
    ansName = null
    for ((name, pairnow) in stuff) {
        if ((pairnow.first == kind) && (pairnow.second <= minPrice)) {
            minPrice = pairnow.second
            ansName = name
        }
    }
    return ansName
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val cSet = chars.map { it.toLowerCase() }.toSet()
    val cWord = word.map { it.toLowerCase() }.toSet()
    return cWord.intersect(cSet) == cWord
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val ans = mutableMapOf<String, Int>()
    for (key in list) {
        ans[key] = ans.getOrDefault(key, 0) + 1
    }

    return ans.filter { it.value != 1 }
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    if (words.size == 1) return false
    for (i in 0 until words.size - 1) {
        val map = mutableMapOf<Char, Int>()
        for (charr in words[i]) {
            map[charr] = map.getOrDefault(charr, 0) + 1
        }
        for (j in i + 1 until words.size) {
            if (words[i] == words[j]) return true
            val map1 = mutableMapOf<Char, Int>()
            for (charr in words[j]) map1[charr] = map1.getOrDefault(charr, 0) + 1
            if (map == map1) return true
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val all = mutableSetOf<String>() //список всех хз зачем
    for ((left, right) in friends) {
        all += left
        all += right
    }
    val answer = mutableMapOf<String, Set<String>>()
    for ((left, right) in friends) {
        val allfriends = mutableSetOf<String>() //то что верну в answer[left]
        val afriends = mutableSetOf<String>() //ОБСЛЕДОВАННЫЕ друзья, чьих друзей я добавил. к ним я больше не обращаюсь
        val bfriends = mutableSetOf<String>() //НЕОБСЛЕДОВАННЫЕ друзья, чьих друзей я еще НЕ добавил.
        bfriends += right
        allfriends += right
        while (bfriends.size != 0) {
            afriends += bfriends
            for (name in bfriends) {
                allfriends += friends[name] ?: setOf()
            }
            bfriends += allfriends
            bfriends.removeAll(afriends)
        }
        allfriends.remove(left)
        answer[left] = allfriends


    }
    for (z in all) {
        answer[z] = answer.getOrDefault(z, setOf())
    }
    print("push")
    return answer
}


/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val newSet = list.toSet()
    val map = mutableMapOf<Int, Int>()
    for (i in newSet)
        map[number - i] = i

    for (i in newSet) {
        if (map.getOrDefault(i, -1) != -1 && list.indexOf(i) != list.lastIndexOf(number - i))
            return list.indexOf(i) to list.lastIndexOf(number - i)
    }

//    for (num in newList) {
//        if (newList.any { it == number - num } && list.indexOf(num) != list.lastIndexOf(number - num))
//            return list.indexOf(num) to list.lastIndexOf(number - num)
//
//    }
    return -1 to -1
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,]
 * которые вы можете унести в рюкзаке.]
 *]
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".]
 *]
 * Например:]
 *   bagPacking(]
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")]
 *   bagPacking(]
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()]
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val mainList = mutableMapOf<Pair<String, Int>, Pair<Int, List<String>>>()
    //                                 |      |          |      ||||||
    //                                имя nowCapacity   цена список сокровищ

    var previousName = "надеюсь такого имени в ваших входных данных не будет, ведь правда?"
    for ((name, digits) in treasures) {   //name - название   digits.first - вес   digits.second - цена
        val price = digits.second
        val weight = digits.first

        for (nowCapacity in 0..capacity) {
            if (weight <= nowCapacity) {  //если можно впихнуть

                //достаю цену из ячейки из предыдущей строки с максимальным весом и складываю с нынешней (newcost)
                //сравниваю с ценой в верхней ячейке (oldcost)
                val newCost = price + ((mainList[previousName to nowCapacity - weight])?.first ?: 0)
                val oldCost = ((mainList[previousName to nowCapacity])?.first ?: 0)

                if (newCost > oldCost) { //если новая стоимость больше
                    mainList[name to nowCapacity] =  //беру из той же ячейки тока не first, a second      + name
                        newCost to (((mainList[previousName to nowCapacity - weight])?.second ?: listOf()) + name)

                } else { //если старая стоимость больше переношу верхнюю ячейку вниз целиком
                    mainList[name to nowCapacity] =
                        oldCost to ((mainList[previousName to nowCapacity])?.second ?: listOf())
                }

            } else { //если не впихнулось
                val oldCost = ((mainList[previousName to nowCapacity])?.first ?: 0)
                mainList[name to nowCapacity] =
                    oldCost to ((mainList[previousName to nowCapacity])?.second ?: listOf())
            }

        }
        previousName = name
    }
    return ((mainList[previousName to capacity])?.second ?: listOf()).toSet()
}
