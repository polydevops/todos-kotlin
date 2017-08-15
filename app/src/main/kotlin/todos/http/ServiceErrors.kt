package todos.http


data class ServiceErrors(val errors: List<ServiceError>?) : Throwable("See internal errors list")