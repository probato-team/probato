package org.probato.manager;

import java.util.UUID;

import org.probato.model.Directory;
import org.probato.model.Manager;
import org.probato.model.Target;

public interface ManagerProvider {

	public void managerHealthCheck(Manager manager);

	public void loadIncrementProject(Manager manager);

	public void sendExecutionData(Manager manager, Target target, Directory directory);

	public Long getIncrement();

	public UUID getProject();

}