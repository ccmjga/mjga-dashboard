class HttpError extends Error {
	constructor(message: string, status: number, detail: string | undefined) {
		super(message);
		this.name = "HttpError";
	}
}

class UnAuthError extends HttpError {
	constructor(status: number, detail?: string) {
		super("身份认证异常", status, detail);
		this.name = "UnAuthError";
	}
}

class ForbiddenError extends HttpError {
	constructor(status: number, detail?: string) {
		super("权限校验异常", status, detail);
		this.name = "ForbiddenError";
	}
}

class SystemError extends HttpError {
	constructor(status: number, detail?: string) {
		super("系统错误，请稍候再试", status, detail);
		this.name = "SystemError";
	}
}

class InternalServerError extends HttpError {
	constructor(status: number, detail: string) {
		super("服务器错误，请稍候再试", status, detail);
		this.name = "InternalServerError";
	}
}

export { UnAuthError, ForbiddenError, SystemError, InternalServerError };
