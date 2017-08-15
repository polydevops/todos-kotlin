package todos.http


data class ServiceError(val title: String, val detail: String) : Throwable("$title: $detail")
