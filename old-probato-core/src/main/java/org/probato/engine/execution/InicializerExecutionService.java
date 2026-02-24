package org.probato.engine.execution;

class InicializerExecutionService {

	InicializerExecutionService() {}

	<T> T newInstance(Class<T> clazz, Object... params) throws ReflectiveOperationException {

		T ret = null;
		if (params.length > 0) {

			var clazzes = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				clazzes[i] = params[i].getClass();
			}
			ret = clazz.getConstructor(clazzes).newInstance(params);

		} else {
			ret = clazz.getConstructor().newInstance();
		}

		return ret;
	}
}