package todos.http


data class ServiceResponse<T>(var data: T?, var errors: List<ServiceError>?)