package by.touchme.newsservice.specification;

import by.touchme.newsservice.criteria.SearchCriteria;
import by.touchme.newsservice.entity.News;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class NewsSpecification implements Specification<News> {

    private final SearchCriteria searchCriteria;

    public NewsSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        return switch (searchCriteria.getOperation()) {
            case CONTAINS -> cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN ->
                    cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch + "%");
            case BEGINS_WITH -> cb.like(cb.lower(root.get(searchCriteria.getKey())), strToSearch + "%");
            case DOES_NOT_BEGIN_WITH ->
                    cb.notLike(cb.lower(root.get(searchCriteria.getKey())), strToSearch + "%");
            case ENDS_WITH -> cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch);
            case DOES_NOT_END_WITH -> cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch);
            case EQUAL -> cb.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case NOT_EQUAL -> cb.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case NULL -> cb.isNull(root.get(searchCriteria.getKey()));
            case NOT_NULL -> cb.isNotNull(root.get(searchCriteria.getKey()));
            case GREATER_THAN ->
                    cb.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL ->
                    cb.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN ->
                    cb.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL ->
                    cb.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        };
    }
}
