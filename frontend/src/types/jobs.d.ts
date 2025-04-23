export interface JobTriggerDto {
	name?: string;
	group?: string;
	className?: string;
	jobDataMap?: {
		dirty?: boolean;
		allowsTransientData?: boolean;
		keys?: string[];
		empty?: boolean;
		wrappedMap?: {
			[key: string]: Record<string, never>;
		};
	} & {
		[key: string]: Record<string, never>;
	};
	triggerName?: string;
	triggerGroup?: string;
	schedulerType?: string;
	cronExpression?: string;
	/** Format: int64 */
	startTime?: number;
	/** Format: int64 */
	endTime?: number;
	/** Format: int64 */
	nextFireTime?: number;
	/** Format: int64 */
	previousFireTime?: number;
	triggerJobDataMap?: {
		dirty?: boolean;
		allowsTransientData?: boolean;
		keys?: string[];
		empty?: boolean;
		wrappedMap?: {
			[key: string]: Record<string, never>;
		};
	} & {
		[key: string]: Record<string, never>;
	};
}
