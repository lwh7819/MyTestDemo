package com.cd.mytestdemo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import java.lang.reflect.Field;

public class InjectUtils {

	public static void inject(Activity activity) {
		try {
			Field[] fields = activity.getClass().getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if (viewInject != null) {
						int viewId = viewInject.id();
						try {
							field.setAccessible(true);
							field.set(activity, activity.findViewById(viewId));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}catch (Exception e){

		}

	}

	public static void inject(View view, Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					int viewId = viewInject.id();
					try {
						field.setAccessible(true);
						field.set(obj, view.findViewById(viewId));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void injectParent(View view, Object obj, Class parentClass) {
		Class _class = obj.getClass();
		if (_class.getSuperclass() != null && parentClass != _class) {
			Class superClass = _class.getSuperclass();
			while (parentClass != superClass) {
				superClass = superClass.getSuperclass();
				if (superClass == null) {
					return;
				}
			}
			Field[] fields = superClass.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if (viewInject != null) {
						int viewId = viewInject.id();
						try {
							field.setAccessible(true);
							field.set(obj, view.findViewById(viewId));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			inject(view, obj);
		}
	}

	public static void injectParent(Dialog dialog, Object obj, Class parentClass) {
		Class _class = obj.getClass();
		if (_class.getSuperclass() != null && parentClass != _class) {
			Class superClass = _class.getSuperclass();
			while (parentClass != superClass) {
				superClass = superClass.getSuperclass();
				if (superClass == null) {
					return;
				}
			}
			Field[] fields = superClass.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if (viewInject != null) {
						int viewId = viewInject.id();
						try {
							field.setAccessible(true);
							field.set(obj, dialog.findViewById(viewId));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			inject(dialog, obj);
		}
	}

	public static void inject(Dialog dialog, Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					int viewId = viewInject.id();
					try {
						field.setAccessible(true);
						field.set(obj, dialog.findViewById(viewId));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}