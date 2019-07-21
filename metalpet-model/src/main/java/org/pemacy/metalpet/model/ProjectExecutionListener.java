package org.pemacy.metalpet.model;

@FunctionalInterface
public interface ProjectExecutionListener {

	void onProjectExecutionStepChange(ExecutionStep step);

}
