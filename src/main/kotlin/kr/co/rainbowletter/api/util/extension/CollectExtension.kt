package kr.co.rainbowletter.api.util.extension

fun <T, K, V> List<T>.toHashMap(
    key: (T) -> K,
    value: (T) -> V
): HashMap<K, V> {
    val result: HashMap<K, V> = HashMap()
    this.forEach { o ->
        result[key(o)] = value(o)
    }
    return result
}
