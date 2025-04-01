import { setupWorker } from "msw/browser";
import permissionHandlers from "./permissionHandlers";
import roleHandlers from "./roleHandlers";
import userHandlers from "./userHandlers";
import authHandlers from "./authHandlers";

export const worker = setupWorker(
	...userHandlers,
	...authHandlers,
	...roleHandlers,
	...permissionHandlers,
);
