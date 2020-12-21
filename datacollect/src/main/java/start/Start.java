package start;

import gitwalk.LightWalker;

public class Start {

	public static void main(String[] args) throws Exception {
		LightWalker walk = new LightWalker();
		walk.searchPotentialRegressions();
	}

}
