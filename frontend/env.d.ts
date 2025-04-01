/// <reference types="vite/client" />
interface ImportMetaEnv {
	readonly VITE_ENABLE_MOCK: "true" | "false";
	readonly VITE_BACKEND_PORT: string;
}
