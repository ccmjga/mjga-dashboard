import { off } from "node:process";
import { fileURLToPath } from "node:url";
import { configDefaults, defineConfig, mergeConfig } from "vitest/config";
import viteConfig from "./vite.config";

export default mergeConfig(
	viteConfig,
	defineConfig({
		test: {
			inspectBrk: false,
			fileParallelism: true,
			testTimeout: 0,
			exclude: [...configDefaults.exclude, "e2e/**"],
			root: fileURLToPath(new URL("./", import.meta.url)),
			browser: {
				enabled: true,
				headless: false,
				provider: "playwright",
				// https://vitest.dev/guide/browser/playwright
				instances: [{ browser: "chromium" }],
				screenshotFailures: false,
			},
		},
	}),
);
