package com.hacareem.finaldestination.webservice.converters;


import com.hacareem.finaldestination.entities.base.FormServiceRequest;
import com.hacareem.finaldestination.entities.base.JsonServiceRequest;
import com.hacareem.finaldestination.entities.base.WebServiceResponse;
import com.hacareem.finaldestination.utilities.JavaUtility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created on 11/02/2016.
 */
@SuppressWarnings("unchecked")
public class ServiceConverterFactory extends Converter.Factory{

    @SuppressWarnings("OverlyComplexBooleanExpression")
    @Override public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                                    Annotation[] parameterAnnotations,
                                                                    Annotation[] methodAnnotations, Retrofit retrofit) {

        Class<?> responseClass = JavaUtility.getClassOfTokenType(type);

        if(JsonServiceRequest.class.isAssignableFrom(responseClass)) {
            return ServiceJsonRequestConverter.INSTANCE;
        }

        if(FormServiceRequest.class.isAssignableFrom(responseClass)) {
            return ServiceFormRequestConverter.INSTANCE;
        }

        if ((type == String.class)
                || (type == boolean.class)
                || (type == Boolean.class)
                || (type == byte.class)
                || (type == Byte.class)
                || (type == char.class)
                || (type == Character.class)
                || (type == double.class)
                || (type == Double.class)
                || (type == float.class)
                || (type == Float.class)
                || (type == int.class)
                || (type == Integer.class)
                || (type == long.class)
                || (type == Long.class)
                || (type == short.class)
                || (type == Short.class)) {
            return ScalarRequestBodyConverter.INSTANCE;
        }

        return null;
    }

    @SuppressWarnings("UnqualifiedInnerClassAccess")
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        Class<?> responseClass = JavaUtility.getClassOfTokenType(type);
        if(WebServiceResponse.class.isAssignableFrom(responseClass)) {
            return new ServiceResponseConverter(type);
        }

        if (type == String.class) {
            return ScalarResponseBodyConverters.StringResponseBodyConverter.INSTANCE;
        }
        if (type == Boolean.class) {
            return ScalarResponseBodyConverters.BooleanResponseBodyConverter.INSTANCE;
        }
        if (type == Byte.class) {
            return ScalarResponseBodyConverters.ByteResponseBodyConverter.INSTANCE;
        }
        if (type == Character.class) {
            return ScalarResponseBodyConverters.CharacterResponseBodyConverter.INSTANCE;
        }
        if (type == Double.class) {
            return ScalarResponseBodyConverters.DoubleResponseBodyConverter.INSTANCE;
        }
        if (type == Float.class) {
            return ScalarResponseBodyConverters.FloatResponseBodyConverter.INSTANCE;
        }
        if (type == Integer.class) {
            return ScalarResponseBodyConverters.IntegerResponseBodyConverter.INSTANCE;
        }
        if (type == Long.class) {
            return ScalarResponseBodyConverters.LongResponseBodyConverter.INSTANCE;
        }
        if (type == Short.class) {
            return ScalarResponseBodyConverters.ShortResponseBodyConverter.INSTANCE;
        }

        return null;
    }
}
