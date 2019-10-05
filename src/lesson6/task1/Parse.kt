@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val input = str.split(" ")

    if (input.size != 3) return ""

    val day: Int
    val month: Int
    val year: Int
    val months = listOf(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )

    try { //формат года?
        year = input[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }

    if (input[1] in months) { //формат месяца?
        month = months.indexOf(input[1]) + 1
    } else return ""

    val rightday = daysInMonth(month, year) //количество дней в месяце

    try { //формат дня?
        day = input[0].toInt()
        if (day > rightday && day > 0) return ""
    } catch (e: NumberFormatException) {
        return ""
    }

    return String.format("%02d.%02d.%d", day, month, year)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val input = digital.split(".")
    if (input.size != 3) return ""

    val day: Int
    val month: Int
    val year: Int
    val months = listOf(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )

    try {                                    //формат месяца?
        month = input[1].toInt()
        if (month !in 1..12) return ""
    } catch (e: NumberFormatException) {
        return ""
    }

    try {                                    //формат года?
        year = input[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }

    val rightday = daysInMonth(month, year)

    try {                                   //формат дня?
        day = input[0].toInt()
        if (day > rightday && day > 0) return ""
    } catch (e: NumberFormatException) {
        return ""
    }

    return String.format("%d %s %d", day, months[month - 1], year)
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val phonelist = mutableListOf<Char>()
    val notTrash = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '(', ')', ' ', '-')
    val motTrash = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '(', ')')

    for (i in phone) { //првоеряю "легален" ли номер и удаляю лишнее
        if (i !in notTrash) return ""
        if (i in motTrash) phonelist.add(i)
    }

    val left = phonelist.indexOf('(')
    val right = phonelist.indexOf(')')

    //СКОБОЧКА
    if (left != -1) { //есть левая cкоб очка?
        if (right == -1) return ""                             //тогда должна быть правая скоб очка
        if (left != phonelist.lastIndexOf('(')) return ""      //левая только одна
        if (right != phonelist.lastIndexOf(')')) return ""     //правая только одна
        if (right - left < 2) return ""                        //между ними должно быть расстояние
        for (i in left + 1 until right) {                      //проверка шо в скоб очке
            if (phonelist[i] == '+') return ""
        }
    } else if (right != -1) return ""         //раз нет левой, то не над и правую

    //ПЛЮСИКИ
    val plus = phonelist.indexOf('+')
    if (plus != 0 && plus != -1) return ""                     //стоит первым
    if (plus != phonelist.lastIndexOf('+')) return ""          //нет второго
    if (plus == 0 && (phonelist.filter { it != '(' && it != ')' }).size == 1) return ""

    return (phonelist.filter { it != '(' && it != ')' }).joinToString(separator = "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val notTrash = "-% "
    val input = jumps.split(" ")
    if (input.isEmpty()) return -1
    val digits = mutableListOf<Int>()

    for (i in input) { //все числа

        try {
            digits.add(i.toInt())
        } catch (e: NumberFormatException) {
            if (i !in notTrash) return -1       //если исключение - это не число. проверка на допустимые символы
        }
    }
    return digits.max() ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val notTrash = "01234567890+%- "

    for (i in jumps) { //на наличие левых символов
        if (i !in notTrash) return -1
    }

    val list0 = jumps.split(" ")
    if (list0.size % 2 != 0) return -1 //если нечетное (не будет пары)

    val list1 = mutableListOf<String>()

    for (i in 0 until list0.size / 2) {  //создаю лист с успешными прыжками, !!!но мб длина некорректна!!!
        if ("+" in list0[2 * i + 1])
            list1.add(list0[2 * i])
    }

    var maxx = -1

    for (i in list1) {
        try {
            if (i.toInt() > maxx) maxx = i.toInt()
        } catch (e: NumberFormatException) {
        }
    }

    return maxx
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (expression == "") throw IllegalArgumentException()  //не пустой ли

    val notTrash = "1234567890 -+"
    for (i in expression) if (i !in notTrash) throw IllegalArgumentException()  //легальность

    val input = expression.split(" ")

    if (input.size % 2 == 0) throw IllegalArgumentException()  //шоб А + А + А + А было (нечет)

    val a = "1234567890"

    fun trysumm(n: Int): Int {  //пытается вернуть Int (без знака)
        for (i in input[n]) {   //используется потому что "+2" переводится в инт
            if (i !in a) throw IllegalArgumentException()
        }
        return input[n].toInt()
    }

    var n = 0
    var answer = trysumm(n)
    for (i in 1..(input.size - 1) / 2) {
        n = 2 * i
        when {
            input[2 * i - 1] == "+" -> answer += trysumm(n)
            input[2 * i - 1] == "-" -> answer -= trysumm(n)
            else -> throw IllegalArgumentException()
        }
    }
    return answer
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val input = str.split(" ")
    var len = 0
    for (i in 0 until input.size - 1) {
        if (input[i].toUpperCase() == input[i + 1].toUpperCase()) {
            return len
        }
        len += input[i].length + 1  // +1 с учетом пробела
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description == "") return ""

    val input = description.split("; ")         //по парам имя - цена
    val list = mutableListOf<String>()          //2n - имя     2n+1 - цена

    for (i in 0 until input.size) {
        val minilist = input[i].split(" ")
        list.add(minilist[0])
        list.add(minilist[1])
    }

    if (list.size % 2 != 0) return ""           //если не по парам

    var answer = ""
    var biggestPrice = -0.000000000000000000000001

    for (i in 0 until list.size / 2) {
        try {                                   //попытка сравнить цену с наибольшей
            if (list[2 * i + 1].toDouble() > biggestPrice) {
                biggestPrice = list[2 * i + 1].toDouble()
                answer = list[2 * i]
            }
        } catch (e: NumberFormatException) {
            return ""
        }
    }
    return answer
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {

    val map = mapOf('M' to 1000, 'D' to 500, 'C' to 100, 'L' to 50, 'X' to 10, 'V' to 5, 'I' to 1)

    for (i in roman) { //легальность
        if (map[i] == null) return -1
    }

    var answer = 0
    var tumblerIXC = 0 //во избежание двух подряд на возрастание IXC
    var tumblerVV = 0 //во избежание двух подряд V / L / D
    var tumblerIIII = 0

    var now: Int
    var next: Int

    for (i in 0 until roman.length - 1) {     //перебор попарно 0-1 1-2 2-3
        now = map[roman[i]] ?: error("")
        next = map[roman[i + 1]] ?: error("")

        if ((now.toString())[0] == '1' && tumblerIIII < 3) {                        //1000 100 10 1
            if (next == now * 10 || next == now * 5) {         //если этот меньше следующего в 5/10 раз (вычитание)
                if (tumblerIXC == 1) {                           //если до этого уже шло вычитание (IX / CD) - сброс
                    return -1
                } else {                                         //вычитаю и активирую тумблер
                    answer -= now
                    tumblerIXC = 1
                }
            } else if (next < now) {                           //если этот больше следующего
                answer += now
                tumblerIXC = 0
            } else if (next == now) {
                answer += now
                tumblerIIII += 1
            } else {
                return -1
            }
            tumblerVV = 0
        } else if (next < now && tumblerVV == 0) {           //5 50 500 если не шли до этого
            answer += now
            tumblerVV = 1
            tumblerIIII = 0
            tumblerIXC = 0
        } else {                                                 //если шли до этого - сброс
            return -1
        }
    }
    answer += map[roman[roman.length - 1]] ?: error("")
    return answer
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
