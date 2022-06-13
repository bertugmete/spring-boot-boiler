package com.mkk.dataaccess.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AbstractQuerySpecification<T> implements Specification<T> {

	private static final Character ESCAPE_CHAR = '/';

	protected boolean addConcatFieldsStartsWithPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName, String... concatFieldNames) {
		String strValue = extractStringValue(fieldName);

		if (strValue != null && strValue.length() > 0 && concatFieldNames != null && concatFieldNames.length > 0) {

			Optional<Expression<String>> concattedExp = Arrays.asList(concatFieldNames).stream()
					.map(s -> cb.concat(root.get(s), "")).reduce((r1, r2) -> cb.concat(cb.concat(r1, " "), r2));
			
			if(concattedExp.isPresent()) {
				predicates.add(cb.like(cb.lower(concattedExp.get()), strValue.toLowerCase(new Locale("tr")) + '%'));
				return true;
			}
		}
		
		return false;
	}
	
	protected boolean addFieldNameStartsWithPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName) {
		String strValue = extractStringValue(fieldName);

		if (strValue != null && strValue.length() > 0) {
			predicates.add(cb.like(cb.lower(root.get(fieldName)), strValue.toLowerCase(new Locale("tr")) + '%'));
			return true;
		} else {
			return false;
		}
	}

	protected boolean addFieldNameLikeWithPredicateLower(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
														 String fieldName) {
			String strValue = extractStringValue(fieldName);

		if (strValue != null && strValue.length() > 0) {
			String escapedValue = getEscapedValue(strValue);
			predicates.add(cb.like(cb.lower(root.get(fieldName)), '%' + escapedValue.toLowerCase(new Locale("tr")) + '%', ESCAPE_CHAR));
			return true;
		} else {
			return false;
		}
	}

	protected boolean addFieldNameLikeWithPredicateUpper(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
													String fieldName) {
		String strValue = extractStringValue(fieldName);

		if (strValue != null && strValue.length() > 0) {
			String escapedValue = getEscapedValue(strValue);
			predicates.add(cb.like(cb.upper(root.get(fieldName)), '%' + escapedValue.toUpperCase(new Locale("tr")) + '%', ESCAPE_CHAR));
			return true;
		} else {
			return false;
		}
	}

	protected boolean addFieldNameStartsWithPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName, String fieldPath) {

		String strValue = extractStringValue(fieldName);

		if (strValue != null && strValue.length() > 0) {
			String[] paths = fieldPath.split("[.]");
			String lowerStrValue = strValue.toLowerCase(new Locale("tr"));
			if (paths.length > 1) {
				Join<Object, Object> current = root.join(paths[0]);
				for (int i = 1; i < paths.length - 1; ++i) {
					current = current.join(paths[i]);
				}
				predicates.add(cb.like(cb.lower(current.get(paths[paths.length - 1])), lowerStrValue + '%'));
			} else {
				predicates.add(cb.like(cb.lower(root.get(paths[paths.length - 1])), lowerStrValue + '%'));
			}

			return true;
		} else {

			return false;
		}

	}

	protected boolean addFieldEqualsPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName) {
		Object value = extractFieldValue(fieldName, false);
		if (value != null) {
			predicates.add(cb.equal(root.get(fieldName), value));
			return true;
		} else {
			return false;
		}

	}

	protected boolean addFieldEqualsIgnoreCasePredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName) {
		Object value = extractFieldValue(fieldName, false);
		if (value != null) {
			predicates.add(cb.equal(cb.lower(root.get(fieldName)), value));
			return true;
		} else {
			return false;
		}

	}

	protected Predicate createFieldGTEorNullPredicate(CriteriaBuilder cb, Root<T> root, String fieldName,
			String fieldPath) {
		Comparable value = (Comparable) extractFieldValue(fieldName, false);
		List<Predicate> predicates = new ArrayList<>();

		QueryPath<? extends T> path = new QueryPath<>(root, fieldPath);
		predicates.add(cb.greaterThanOrEqualTo(path.getPath(), value));
		predicates.add(cb.isNull(path.getPath()));
		return cb.or(predicates.toArray(new Predicate[predicates.size()]));

	}

	protected Predicate createFieldLTEorNullPredicate(CriteriaBuilder cb, Root<T> root, String fieldName,
			String fieldPath) {
		Comparable value = (Comparable) extractFieldValue(fieldName, false);
		List<Predicate> predicates = new ArrayList<>();

		QueryPath<? extends T> path = new QueryPath<>(root, fieldPath);
		predicates.add(cb.lessThanOrEqualTo(path.getPath(), value));
		predicates.add(cb.isNull(path.getPath()));
		return cb.or(predicates.toArray(new Predicate[predicates.size()]));

	}

	protected boolean addFieldEqualsPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName, String fieldPath) {
		Object val = extractFieldValue(fieldName, false);

		if (!isEmpty(val)) {
			String[] paths = fieldPath.split("[.]");

			if (paths.length > 1) {
				Join<Object, Object> current = root.join(paths[0]);
				for (int i = 1; i < paths.length - 1; ++i) {
					current = current.join(paths[i]);
				}
				predicates.add(cb.equal(current.get(paths[paths.length - 1]), val));
			} else {
				predicates.add(cb.equal(root.get(paths[paths.length - 1]), val));
			}
			return true;
		} else {

			return false;
		}

	}

	protected boolean addFieldInPredicate(Root<T> root, List<Predicate> predicates,
			String fieldName) {
		Object value = extractFieldValue(fieldName, false);
		if (value != null) {
			Expression<String> exp = root.get(fieldName);
			Predicate predicate = exp.in((List<Object>) value);
			predicates.add(predicate);

			return true;
		} else {
			return false;
		}

	}

	private boolean isEmpty(Object val) {
		if (val instanceof String) {
			return ((String) val).length() == 0;
		} else {
			return val == null;
		}

	}

	protected boolean addDateGreaterThanOrEqualToPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName, String fieldPath) {
		Date value = extractDateValue(fieldName);
		if (value != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get(fieldPath), value));
			return true;
		} else {
			return false;
		}
	}

	protected boolean addDateLessThanOrEqualToPredicate(CriteriaBuilder cb, Root<T> root, List<Predicate> predicates,
			String fieldName, String fieldPath) {
		Date value = extractDateValue(fieldName);
		if (value != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get(fieldPath), value));
			return true;
		} else {
			return false;
		}
	}
	
    protected boolean addDateGreaterThanOrEqualToPredicateNoFormatting(CriteriaBuilder cb, Root<T> root,
	    List<Predicate> predicates, String fieldName, String fieldPath) {
	Date value = (Date) extractFieldValue(fieldName, false);
	if (value != null) {
	    predicates.add(cb.greaterThanOrEqualTo(root.get(fieldPath), value));
	    return true;
	} else {
	    return false;
	}
    }

    protected boolean addDateLessThanOrEqualToPredicateNoFormatting(CriteriaBuilder cb, Root<T> root,
	    List<Predicate> predicates, String fieldName, String fieldPath) {
	Date value = (Date) extractFieldValue(fieldName, false);
	if (value != null) {
	    predicates.add(cb.lessThanOrEqualTo(root.get(fieldPath), value));
	    return true;
	} else {
	    return false;
	}
    }

	protected String extractStringValue(String fieldName) {
		Object val = extractFieldValue(fieldName, false);

		String strValue = null;
		if (val != null) {
			strValue = (String) val;
		}
		return strValue;
	}

	protected String extractStringsValue(String fieldName) {
		Object val = extractFieldValue(fieldName, false);

		String strValue = null;
		if (val != null) {
			strValue = (String) val;
		}
		return strValue;
	}

	protected Date extractDateValue(String fieldName) {
		Object val = extractFieldValue(fieldName, false);
		Date dateValue = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (val != null) {
			try {
				dateValue = formatter.parse(String.valueOf(val));
			} catch (Exception e) {

				return null;
			}
		}
		return dateValue;
	}

	protected Object extractFieldValue(String fieldName, boolean isBaseClassField) {
		try {
			Field field = this.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(this);
		} catch (NoSuchFieldException nsfException) {
			if (isBaseClassField) {
				throw new RuntimeException(nsfException);
			}
			return extractNonBaseClassFieldValue(fieldName);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	protected Object extractNonBaseClassFieldValue(String fieldName) {
		try {
			Field field = this.getClass().getField(fieldName);
			field.setAccessible(true);
			return field.get(this);
		} catch (NoSuchFieldException nsfException) {
				throw new RuntimeException(nsfException);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	private String getEscapedValue(String strValue) {
		return strValue
				.replace("/", ESCAPE_CHAR + "/")
				.replace("_", ESCAPE_CHAR + "_")
				.replace("%", ESCAPE_CHAR + "%");
	}
}
