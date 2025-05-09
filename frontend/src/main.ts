import "./assets/main.css";

import { createPinia } from "pinia";
import { createApp } from "vue";

import App from "./App.vue";
import useUserAuth from "./composables/auth/useUserAuth";
import useAlertStore from "./composables/store/useAlertStore";
import router from "./router";
import makeErrorHandler from "./utils/errorHandler";

async function enableMocking() {
	if (import.meta.env.VITE_ENABLE_MOCK === "false") {
		return;
	}

	const { worker } = await import("./api/mocks/setup");

	// `worker.start()` returns a Promise that resolves
	// once the Service Worker is up and ready to intercept requests.
	return worker.start();
}

enableMocking().then(() => {
	const app = createApp(App);
	app.use(createPinia());
	const { signOut } = useUserAuth();
	const { showAlert } = useAlertStore();
	app.use(router);
	const errorHandler = makeErrorHandler(router, signOut, showAlert);
	app.config.errorHandler = errorHandler;
	app.mount("#app");
});
