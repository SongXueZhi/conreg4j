package gitwalk.api;

import org.eclipse.jgit.lib.Repository;

public interface RepositoryProvider {
	Repository get() throws Exception;
}
