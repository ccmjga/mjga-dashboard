import type { ComponentPublicInstance } from "vue";
import type { Router } from "vue-router";
import { RoutePath } from "../router/constants";
import {
	ForbiddenError,
	InternalServerError,
	SystemError,
	UnAuthError,
} from "../types/error";

const errorHandler =
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
		if (err instanceof InternalServerError) {
			showAlert({
				level: "error",
				content: err.message,
			});
			return;
		}
	};

export default errorHandler;
