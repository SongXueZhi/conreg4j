package gitwalk;


import constant.Configuration;
import gitwalk.api.RepositoryProvider;
import gitwalk.iml.RepositoryProviderCloneImpl;
import gitwalk.iml.RepositoryProviderExistingClientImpl;

public class Provider {
	public static final int EXISITING = 0;
	public static final int CLONE = 1;

	public RepositoryProvider create(int providerType) {
		if (providerType==EXISITING) {
			return new RepositoryProviderExistingClientImpl(Configuration.LOCAL_PROJECT);
		}else if (providerType == CLONE) {
			return new RepositoryProviderCloneImpl(Configuration.CLONE_URL,Configuration.LOCAL_PATH);
		}else {
			return new RepositoryProviderExistingClientImpl(Configuration.LOCAL_PROJECT);
		}
	}
}