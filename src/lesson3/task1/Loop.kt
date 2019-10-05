@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.*

fun answer1(digit: Int, kvo: Int, len: Int, n: Int): Int = (digit / 10.0.pow(kvo + len - n).toInt()) % 10

fun slog(x1: Double, n: Int): Double {
    var x2 = x1.pow(n)
    x2 /= factorial(n)
    return x2
}

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int = if (n / 10 == 0) 1 else digitNumber(n / 10) + 1

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n == 1) return 1
    if (n == 2) return 1
    var x = 1
    var y = 1
    var z = 0
    for (i in 1..n - 2) {
        z = x + y
        x = y
        y = z
    }
    return z
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */

fun lcm(m: Int, n: Int): Int {
    var m1 = m
    var n1 = n
    while ((m1 != 0) && (n1 != 0)) {
        if (m1 > n1) m1 %= n1
        else n1 %= m1
    }
    val gcd = m1 + n1
    return m * n / gcd

}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (z in 2..(sqrt(n.toDouble())).toInt()) {
        if (n % z == 0) return z
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    for (z in 2..min(m, n)) if ((m % z == 0) and (n % z == 0)) return false
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var m1 = sqrt(m.toDouble())
    if (m1 % 1 == 0.0) m1.toInt()
    else m1 = ceil(m1)
    return m1 <= sqrt(n.toDouble())
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1ику
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var s = 0
    var q = x
    while (q != 1) {
        if (q % 2 == 0) {
            q /= 2
            s += 1
        } else {
            q = (q * 3 + 1) / 2
            s += 2
        }
    }
    return s
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    val x1 = x % (2 * PI)
    var sum = 0.0
    var n = 1
    while (abs(slog(x1, n)) > eps) {
        sum += if (n % 4 != 3) slog(x1, n) else -slog(x1, n)
        n += 2
    }
    return sum
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    val x1 = x % (2 * PI)
    var sum = 0.0
    var n = 0
    while (abs(slog(x1, n)) > eps) {
        sum += if ((n / 2) % 2 == 0) slog(x1, n) else -slog(x1, n)
        n += 2
    }
    return sum
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var s = 0
    var n1 = n
    while (n1 > 0) {
        s = s * 10 + n1 % 10
        n1 /= 10
    }
    return s
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = (n == revert(n))

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var n1 = n / 10
    val a = n % 10
    while (n1 != 0) {
        if (n1 % 10 == a) n1 /= 10 else return true
    }
    return false
}

/** Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 1 4 9 16 25 36 49 64 81 100 121 144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var kvo = 0
    var sqr = 1
    var plus = 3
    var len = (log10(sqr.toDouble())).toInt() + 1
    while (n > kvo + len) {
        kvo += len
        sqr += plus
        plus += 2
        len = (log10(sqr.toDouble())).toInt() + 1
    }
    return answer1(sqr, kvo, len, n)

}

/**++
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var kvo = 0
    var number = 1
    var len = (log10(fib(number).toDouble())).toInt() + 1
    while (n > kvo + len) {
        kvo += len
        number++
        len = (log10(fib(number).toDouble())).toInt() + 1
    }
    return answer1(fib(number), kvo, len, n)
}
