class HttpError extends Error {
	status: number;
	detail: string | undefined;
	constructor(message: string, status: number, detail: string | undefined) {
		super(message);
		this.name = "HttpError";
		this.status = status;
		this.detail = detail;
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
	constructor(status: number, detail?: string) {
		super("服务器错误，请稍候再试", status, detail);
		this.name = "InternalServerError";
	}
}

class BadRequestError extends HttpError {
	constructor(status: number, detail?: string) {
		super("请求非法", status, detail);
		this.name = "BadRequestError";
	}
}

export {
	UnAuthError,
	ForbiddenError,
	SystemError,
	InternalServerError,
	BadRequestError,
};
