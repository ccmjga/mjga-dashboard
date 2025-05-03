import { setupWorker } from "msw/browser";
import authHandlers from "./authHandlers";
import jobHandlers from "./jobHandlers";
import permissionHandlers from "./permissionHandlers";
import roleHandlers from "./roleHandlers";
import userHandlers from "./userHandlers";
import departmentHandlers from "./departmentHandlers";
export const worker = setupWorker(
	...userHandlers,
	...authHandlers,
	...roleHandlers,
	...permissionHandlers,
	...jobHandlers,
	...departmentHandlers,
);
