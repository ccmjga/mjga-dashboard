/// <reference types="vite/client" />
interface ViteTypeOptions {
	// By adding this line, you can make the type of ImportMetaEnv strict
	// to disallow unknown keys.
	strictImportMetaEnv: unknown;
}

interface ImportMetaEnv {
	readonly VITE_ENABLE_MOCK: "true" | "false";
	readonly VITE_BACKEND_PORT: string;
	readonly VITE_APP_PORT: string;
}

interface ImportMeta {
	readonly env: ImportMetaEnv;
}

interface AppConfig {
	errorHandler?: (
		err: unknown,
		instance: ComponentPublicInstance | null,
		info: string,
	) => void;
}
