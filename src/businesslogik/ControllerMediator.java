package businesslogik;
public class ControllerMediator {
	
	
	
	
	private ControllerMediator() {
		
	}

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }

}
