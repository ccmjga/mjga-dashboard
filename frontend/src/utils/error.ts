import type { ComponentPublicInstance } from "vue";
import type { Router } from "vue-router";
import { RoutePath } from "../router/constants";
import {
	ForbiddenError,
	AppServerError,
	SystemError,
	UnAuthError,
} from "../types/error";

const makeErrorHandler =
	(
		router: Router,
		signOut: () => void,
		showAlert: ({
			content,
			level,
		}: {
			content: string;
			level: "info" | "success" | "warning" | "error";
		}) => void,
	) =>
	(err: unknown, instance: ComponentPublicInstance | null, info: string) => {
		if (err instanceof UnAuthError) {
			signOut();
			router.push(RoutePath.LOGIN);
			showAlert({
				level: "error",
				content: err.message,
			});
			return;
		}
		if (err instanceof ForbiddenError) {
			showAlert({
				level: "error",
				content: err.message,
			});
			return;
		}
		if (err instanceof SystemError) {
			showAlert({
				level: "error",
				content: err.message,
			});
			return;
		}
		if (err instanceof AppServerError) {
			showAlert({
				level: "error",
				content: err.message,
			});
			return;
		}
	};

export default makeErrorHandler;
