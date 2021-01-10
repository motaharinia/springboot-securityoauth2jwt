package com.motaharinia.authorizationserver.config.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Component
public class ExceptionManager {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionManager.class);

    @Autowired
    @Qualifier(value = "SupportOrchServiceImpl")
    private SupportOrchService supportOrchService;

    @Autowired
    @Qualifier(value = "LoggedInServiceImpl")
    private LoggedInService loggedInService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @RequestMapping(headers = "x-requested-with=XMLHttpRequest", produces = "application/json")
    public @ResponseBody
    CustomExceptionModel doException(Exception exception, HttpServletRequest request, Locale locale, HttpServletResponse response) throws Exception {
        String className = "null";
        if (exception.getClass() != null) {
            className = exception.getClass().getSimpleName();
        }
        logger.info("EEEEEEEEEE Start: ex.getClass().getSimpleName(): " + className + " exception : " + exception);

        CustomExceptionModel customExceptionModel = getCustomExceptionModel(exception, request);
        if (loggedInService != null) {
            UserAuthenticatedModel userAuthenticatedModel = loggedInService.getLoggedInUserAuthenticatedModel();
            if (userAuthenticatedModel != null) {
                customExceptionModel.setUsername(userAuthenticatedModel.getUsername());
                customExceptionModel.setUserId(userAuthenticatedModel.getId());
            }
        }
        if (customExceptionModel.getCustomExceptionDetailModelList() != null) {
            if ((customExceptionModel.getCustomExceptionDetailModelList().size() > 0)
                    && (customExceptionModel.getCustomExceptionDetailModelList().get(0) != null)
                    && (customExceptionModel.getCustomExceptionDetailModelList().get(0).getMessage() != null)
                    && (customExceptionModel.getCustomExceptionDetailModelList().get(0).getMessage().get("error") != null)
                    && (!"businessexception.common.LoggedInServiceImpl.loggedInUserIsNull".toLowerCase().equals(customExceptionModel.getCustomExceptionDetailModelList().get(0).getMessage().get("error").toLowerCase()))) {
                customExceptionModel = supportOrchService.customExceptionCreate(customExceptionModel);
            }
        }
        return customExceptionModel;
    }

    public static CustomExceptionModel getCustomExceptionModel(Exception exception, HttpServletRequest request) {
        CustomExceptionModel customExceptionModel = new CustomExceptionModel();

        if (exception != null) {
            List<CustomExceptionDetailModel> customExceptionDetailModelList = new ArrayList<>();
            if (exception.getClass() != null) {
                if (exception.getClass().equals(ListException.class)) {
                    customExceptionModel = getModelFromListException((ListException) exception);
                } else if (exception.getClass().equals(MethodArgumentNotValidException.class)) {
                    //prepare MethodArgumentNotValidException to custom exception model
                    customExceptionDetailModelList.add(getModelFromMethodArgumentNotValidException((MethodArgumentNotValidException) exception, null));
                    customExceptionModel.setCustomExceptionDetailModelList(customExceptionDetailModelList);
                    customExceptionModel.setType(CustomExceptionModelType.VALIDATIONEXCEPTION);
                } else if (exception.getClass().equals(ValidationException.class)) {
                    //prepare ValidationException to customExceptionModel
                    customExceptionDetailModelList.add(getModelFromValidationException((ValidationException) exception, null));
                    customExceptionModel.setCustomExceptionDetailModelList(customExceptionDetailModelList);
                    customExceptionModel.setType(CustomExceptionModelType.VALIDATIONEXCEPTION);
                } else if (exception.getClass().equals(BusinessException.class)) {
                    customExceptionDetailModelList.add(getModelFromBusinessException((BusinessException) exception, null));
                    customExceptionModel.setCustomExceptionDetailModelList(customExceptionDetailModelList);
                    customExceptionModel.setType(CustomExceptionModelType.BUSINESSEXCEPTION);
                } else {
                    customExceptionDetailModelList.add(getModelFromGeneralException(exception, null));
                    customExceptionModel.setCustomExceptionDetailModelList(customExceptionDetailModelList);
                    customExceptionModel.setType(CustomExceptionModelType.GENERALEXCEPTION);
                }
            }

            logger.info(" customExceptionModel.getType():" + customExceptionModel.getType() + " customExceptionModel:" + customExceptionModel);

            if (request != null) {
                String url = request.getServletPath();
                String ipAddress = request.getHeader("X-FORWARDED-FOR");
                if (ipAddress == null) {
                    ipAddress = request.getRemoteAddr();
                }
                customExceptionModel.setUrl(url);
                customExceptionModel.setIpAddress(ipAddress);
            }
            customExceptionModel.setCustomExceptionModelMode(RunConfiguration.getModel().getCustomExceptionModeEnum());

        }
        return customExceptionModel;
    }

    private static CustomExceptionModel getModelFromListException(ListException listException) {

        CustomExceptionModel customExceptionModel = new CustomExceptionModel();
        List<CustomExceptionDetailModel> customExceptionDetailModelList = new ArrayList<CustomExceptionDetailModel>();
        logger.info("=========ExceptionManager.getModelFromListException: listException.getExceptionList().size():" + listException.getExceptionList().size());
        for (Map.Entry<Exception, String> entry : listException.getExceptionList().entrySet()) {
            Exception exception = entry.getKey();
            String relatedToId = entry.getValue();
            Integer id = null;
            if (relatedToId != "") {
                id = Integer.parseInt(relatedToId);
            }
            logger.info("=========ExceptionManager.getModelFromListException: relatedToId:" + relatedToId + " id:" + id);
            if (entry.getKey().getClass().equals(MethodArgumentNotValidException.class)) {
                customExceptionDetailModelList.add(getModelFromMethodArgumentNotValidException((MethodArgumentNotValidException) exception, id));
            } else if (entry.getKey().getClass().equals(ValidationException.class)) {
                customExceptionDetailModelList.add(getModelFromValidationException((ValidationException) exception, id));
            } else if (entry.getKey().getClass().equals(BusinessException.class)) {
                customExceptionDetailModelList.add(getModelFromBusinessException((BusinessException) exception, id));
            } else {
                customExceptionDetailModelList.add(getModelFromGeneralException(exception, id));
            }
        }

        logger.info("=========ExceptionManager.getModelFromListException: customExceptionDetailModelList:" + customExceptionDetailModelList);
        customExceptionModel.setCustomExceptionDetailModelList(customExceptionDetailModelList);
        customExceptionModel.setType(CustomExceptionModelType.LISTEXCEPTION);
        return customExceptionModel;
    }

    private static CustomExceptionDetailModel getModelFromMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, Integer id) {
        HashMap<String, String> fieldValidationError = new HashMap<>();
        BindingResult result = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String modelName = fieldError.getObjectName();
            String modelPrefix;
            String relatedToFieldName;
            if (modelName == null || modelName.equals("")) {
                relatedToFieldName = fieldError.getField();
            } else {
                modelPrefix = getModelPrefixFromModelName(modelName);
                relatedToFieldName = modelPrefix + "." + modelPrefix + "[" + fieldError.getField() + "]";
            }
            logger.info(":::::::::: modelName: " + modelName + " relatedToFieldName: " + relatedToFieldName + " fieldError.getDefaultMessage():" + fieldError.getDefaultMessage());
            fieldValidationError.put(relatedToFieldName, fieldError.getDefaultMessage());
        }
        String relatedToId = "";
        if (id != null) {
            relatedToId = id.toString();
        }
        CustomExceptionDetailModel customExceptionDetailModel = new CustomExceptionDetailModel(methodArgumentNotValidException, relatedToId, CustomExceptionModelType.VALIDATIONEXCEPTION, "", fieldValidationError);
        return customExceptionDetailModel;
    }

    private static CustomExceptionDetailModel getModelFromValidationException(ValidationException validationException, Integer id) {
        HashMap<String, String> fieldValidationError = new HashMap<>();
        List<FieldError> fieldErrors = new ArrayList<>();
        FieldError error = new FieldError(validationException.getClassName(), validationException.getFieldName(), validationException.getFieldValidationError());
        fieldErrors.add(error);
        for (FieldError fieldError : fieldErrors) {
            String modelName = fieldError.getObjectName();
            String modelPrefix;
            String relatedToFieldName;
            if (modelName == null || modelName.equals("")) {
                relatedToFieldName = fieldError.getField();
            } else {
                modelPrefix = getModelPrefixFromModelName(modelName);
                relatedToFieldName = modelPrefix + "." + modelPrefix + "[" + fieldError.getField() + "]";
            }
            logger.info(":::::::::: modelName: " + modelName + " relatedToFieldName: " + relatedToFieldName + " fieldError.getDefaultMessage():" + fieldError.getDefaultMessage());
            fieldValidationError.put(relatedToFieldName, fieldError.getDefaultMessage());
        }
        String relatedToId = "";
        if (id != null) {
            relatedToId = id.toString();
        }
        CustomExceptionDetailModel customExceptionDetailModel = new CustomExceptionDetailModel(validationException, relatedToId, CustomExceptionModelType.VALIDATIONEXCEPTION, validationException.getDescription(), fieldValidationError);
        return customExceptionDetailModel;
    }

    private static CustomExceptionDetailModel getModelFromBusinessException(BusinessException businessException, Integer id) {
        String relatedToId = "";
        if (id != null) {
            relatedToId = id.toString();
        }
        CustomExceptionDetailModel customExceptionDetailModel = new CustomExceptionDetailModel(businessException, relatedToId, CustomExceptionModelType.BUSINESSEXCEPTION, businessException.getDescription());
        return customExceptionDetailModel;
    }

    private static CustomExceptionDetailModel getModelFromGeneralException(Exception exception, Integer id) {
        String relatedToId = "";
        if (id != null) {
            relatedToId = id.toString();
        }
        CustomExceptionDetailModel customExceptionDetailModel = new CustomExceptionDetailModel(exception, relatedToId, CustomExceptionModelType.GENERALEXCEPTION, "");
        return customExceptionDetailModel;
    }

    private static String getModelPrefixFromModelName(String modelName) {
        String modelPrefix = modelName;
        Integer modelPrefixIndex = modelName.toLowerCase().indexOf("frontmodel");
        if (modelPrefixIndex > -1) {
            modelPrefix = modelName.substring(0, modelPrefixIndex);
        } else {
            modelPrefixIndex = modelName.toLowerCase().indexOf("model");
            if (modelPrefixIndex > -1) {
                modelPrefix = modelName.substring(0, modelPrefixIndex);
            }
        }
        modelPrefix = modelPrefix.substring(0, 1).toLowerCase() + modelPrefix.substring(1);
        return modelPrefix;
    }

}
