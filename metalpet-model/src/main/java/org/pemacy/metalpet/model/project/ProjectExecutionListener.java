package org.pemacy.metalpet.model.project;

@FunctionalInterface
public interface ProjectExecutionListener {

	void onProjectExecutionStepChange(ExecutionStep step);

}
