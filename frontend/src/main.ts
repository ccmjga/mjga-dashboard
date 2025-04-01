import "./assets/main.css";

import { createPinia } from "pinia";
import { createApp } from "vue";

import App from "./App.vue";
import router from "./router";

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
	app.use(router);
	app.mount("#app");
});
