package valuetypes;


public class CheckboxType extends AbstractValueType<Boolean> {
	
	@Override
	public Class<Boolean> getTypeClass() {
		return Boolean.class;
	}

	@Override
	public String getName() {
		return "checkbox";
	}
	
	@Override
	public boolean isValid(Boolean input) {
		return true;
	}

	@Override
	public String save(Object settingValue) {
		return getTypeClass().cast(settingValue).toString();
	}

	@Override
	public Boolean load(String savedData) {
		return Boolean.parseBoolean(savedData);
	}



}
