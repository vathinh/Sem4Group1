package com.aptech.group.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotExistValidator implements ConstraintValidator<NoExistRecord, Object> {

  @PersistenceContext
  private EntityManager entityManager;

  private String fieldName;

  private boolean isAddDeleteFlag;
  private Class<?> entityName;

  @Override
  public void initialize(final NoExistRecord constraintAnnotation) {
    entityName = constraintAnnotation.entityName();
    fieldName = constraintAnnotation.fieldName();
    isAddDeleteFlag = constraintAnnotation.isDeleteFlag();
  }

  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    String queryName = isAddDeleteFlag ? String.format(
        "SELECT obj FROM %s obj  WHERE obj.%s = :value and deleteFlag=false",
        entityName.getName(), fieldName)
        : String.format("SELECT obj FROM %s obj  WHERE obj.%s = :value",
            entityName.getName(), fieldName);
    Query query = entityManager.createQuery(queryName);
    query.setParameter("value", value);
    Long count = query.getResultList().stream().count();
    return count < 1;
  }
}
