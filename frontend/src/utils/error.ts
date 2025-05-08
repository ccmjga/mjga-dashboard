import type { ComponentPublicInstance } from "vue";
import { useRouter, type Router } from "vue-router";
import useAlertStore from "../composables/store/useAlertStore";
import { RoutePath } from "../router/constants";
import {
	ForbiddenError,
	InternalServerError,
	SystemError,
	UnAuthError,
} from "../types/error.d";
import type { AlertProps } from "../types/alert";

const errorHandler =
	(
		router: Router,
		signOut: () => void,
		showAlert: (alert: AlertProps) => void,
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
