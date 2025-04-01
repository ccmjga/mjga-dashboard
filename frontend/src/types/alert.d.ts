export interface AlertProps {
	content?: string;
	level?: "info" | "success" | "warning" | "error";
	isShow?: boolean;
	timer?: number;
}
