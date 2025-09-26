package co.com.prueba.usecase.enums;


public enum ErrorMessages {
    
    // Branch errors
    BRANCH_NAME_BLANK("Branch name cannot be blank"),
    BRANCH_FRANCHISE_ID_INVALID("Branch franchise id cannot be 0"),
    BRANCH_NOT_CREATED("Branch not created"),
    BRANCH_NOT_UPDATED("Branch not updated"),
    BRANCH_NOT_FOUND("Branch with id %d not found"),
    BRANCH_ID_INVALID("Branch Id cannot be 0"),
    
    // Franchise errors
    FRANCHISE_NOT_FOUND("Franchise with id %d not found"),
    FRANCHISE_NAME_BLANK("Franchise name cannot be blank"),
    FRANCHISE_ID_INVALID("Franchise ID cannot be 0 or negative"),
    FRANCHISE_NAME_EXISTS("Franchise name already exists"),
    FRANCHISE_NOT_CREATED("Franchise not created"),
    FRANCHISE_NOT_UPDATED("Franchise not updated"),
    
    // Product errors
    PRODUCT_NAME_BLANK("Product name cannot be blank"),
    PRODUCT_STOCK_INVALID("Stock cannot be 0"),
    PRODUCT_BRANCH_ID_INVALID("Branch id cannot be 0"),
    PRODUCT_ID_INVALID("Product ID cannot be 0"),
    PRODUCT_NOT_FOUND("Product with id %d not found"),
    PRODUCT_NOT_CREATED("Product not created"),
    PRODUCT_NOT_UPDATED("Product not updated"),
    PRODUCT_NOT_DELETED("Product not deleted"),
    PRODUCTS_NOT_FOUND_FOR_FRANCHISE("Not found products for franchise with id %d"),
    
    BODY_CANNOT_BE_NULL("The body cannot be null"),
    FIELD_REQUIRED("Field %s is required"),
    INVALID_NUMBER_FORMAT("Field %s must be a valid number"),
    NAME_REQUIRED("name is required"),
    STOCK_REQUIRED("stock is required"),
    STOCK_INVALID_NUMBER("stock must be a valid number"),
    PRODUCT_DELETED_SUCCESS("Product deleted successfully");
    
    private final String message;
    
    ErrorMessages(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getFormattedMessage(Object... params) {
        return String.format(message, params);
    }
}