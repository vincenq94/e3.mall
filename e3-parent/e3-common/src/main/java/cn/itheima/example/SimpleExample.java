package cn.itheima.example;


import cn.itheima.utils.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.entity.IDynamicTableName;
import tk.mybatis.mapper.entity.SqlsCriteria;
import tk.mybatis.mapper.util.MetaObjectUtil;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.util.StringUtil;

import java.util.*;

public class SimpleExample implements IDynamicTableName {

	protected String orderByClause;

    protected boolean distinct;

    protected boolean exists;

    protected boolean notNull;

    protected boolean forUpdate;

    //查询字段
    protected Set<String> selectColumns;

    protected String countColumn;

    protected List<Criteria> oredCriteria;

    protected SimpleExample.Criteria currentCriteria;

    protected SimpleExample.OrderBy ORDERBY;

    /**
     * 默认exists为true
     */
    public SimpleExample() {
        this(true);
    }

    /**
     * 带exists参数的构造方法，默认notNull为false，允许为空
     *
     * @param exists - true时，如果字段不存在就抛出异常，false时，如果不存在就不使用该字段的条件
     */
    private SimpleExample(boolean exists) {
        this(exists, false);
    }

    /**
     * 带exists参数的构造方法
     *
     * @param exists  - true时，如果字段不存在就抛出异常，false时，如果不存在就不使用该字段的条件
     * @param notNull - true时，如果值为空，就会抛出异常，false时，如果为空就不使用该字段的条件
     */
    private SimpleExample(boolean exists, boolean notNull) {
        this.exists = exists;
        this.notNull = notNull;
        oredCriteria = new ArrayList<Criteria>();
        currentCriteria = createCriteria();
        this.ORDERBY = new OrderBy(this);
    }


    private SimpleExample(SimpleExample.Builder builder) {
        this.exists = builder.exists;
        this.notNull = builder.notNull;
        this.distinct = builder.distinct;
        this.selectColumns = builder.selectColumns;
        this.oredCriteria = builder.exampleCriterias;
        this.forUpdate = builder.forUpdate;

        if (!StringUtil.isEmpty(builder.orderByClause.toString())) {
            this.orderByClause = builder.orderByClause.toString();
        }
    }

    public static SimpleExample.Builder builder() {
        return new SimpleExample.Builder();
    }

    public SimpleExample.OrderBy orderBy(String property) {
        this.ORDERBY.orderBy(property);
        return this.ORDERBY;
    }


    /**
     * 指定要查询的属性列 - 这里会自动映射到表字段
     *
     * @param properties
     * @return
     */
    public SimpleExample selectProperties(String... properties) {
        if (properties != null && properties.length > 0) {
            if (this.selectColumns == null) {
                this.selectColumns = new LinkedHashSet<String>();
            }
            for (String property : properties) {
                this.selectColumns.add(property);
            }
        }
        return this;
    }

    public SimpleExample isNull(String property) {
        this.currentCriteria.andIsNull(property);
        return this;
    }

    public SimpleExample isNotNull(String property) {
        this.currentCriteria.andIsNotNull(property);
        return this;
    }

    public SimpleExample eq(String property, Object value) {
        this.currentCriteria.andEqualTo(property, value);
        return this;
    }

    public SimpleExample neq(String property, Object value) {
        this.currentCriteria.andNotEqualTo(property, value);
        return this;
    }

    public SimpleExample gt(String property, Object value) {
        this.currentCriteria.andGreaterThan(property, value);
        return this;
    }

    public SimpleExample gte(String property, Object value) {
        this.currentCriteria.andGreaterThanOrEqualTo(property, value);
        return this;
    }

    public SimpleExample lt(String property, Object value) {
        this.currentCriteria.andLessThan(property, value);
        return this;
    }

    public SimpleExample lte(String property, Object value) {
        this.currentCriteria.andLessThanOrEqualTo(property, value);
        return this;
    }

    public SimpleExample in(String property, Iterable values) {
        this.currentCriteria.andIn(property, values);
        return this;
    }

    public SimpleExample notIn(String property, Iterable values) {
        this.currentCriteria.andNotIn(property, values);
        return this;
    }

    public SimpleExample between(String property, Object value1, Object value2) {
        this.currentCriteria.andBetween(property, value1, value2);
        return this;
    }

    public SimpleExample notBetween(String property, Object value1, Object value2) {
        this.currentCriteria.andNotBetween(property, value1, value2);
        return this;
    }

    public SimpleExample like(String property, String value) {
        this.currentCriteria.andLike(property, value);
        return this;
    }

    public SimpleExample notLike(String property, String value) {
        this.currentCriteria.andNotLike(property, value);
        return this;
    }

    /**
     * 手写条件
     *
     * @param condition 例如 "length(countryname)<5"
     * @return
     */
    public SimpleExample andCondition(String condition) {
        this.currentCriteria.addCriterion(condition);
        return this;
    }


    /**
     * 将此对象的不为空的字段参数作为相等查询条件
     *
     * @param param 参数对象
     * @author Bob {@link}0haizhu0@gmail.com
     * @Date 2015年7月17日 下午12:48:08
     */
    public SimpleExample eq(Object param) {
        MetaObject metaObject = MetaObjectUtil.forObject(param);
        String[] properties = metaObject.getGetterNames();
        for (String property : properties) {
            //属性和列对应Map中有此属性
            Object value = metaObject.getValue(property);
            //属性值不为空
            if (value != null) {
                eq(property, value);
            }
        }
        return this;
    }

    /**
     * 将此对象的所有字段参数作为相等查询条件，如果字段为 null，则为 is null
     *
     * @param param 参数对象
     */
    public SimpleExample allEq(Object param) {
        MetaObject metaObject = MetaObjectUtil.forObject(param);
        String[] properties = metaObject.getGetterNames();
        for (String property : properties) {
            //属性和列对应Map中有此属性
            Object value = metaObject.getValue(property);
            //属性值不为空
            if (value != null) {
                eq(property, value);
            } else {
                isNull(property);
            }
        }
        return this;
    }

    public void or(SimpleExample.Criteria criteria) {
        criteria.setAndOr("or");
        oredCriteria.add(criteria);
    }

    public SimpleExample or() {
        SimpleExample.Criteria criteria = createCriteriaInternal();
        criteria.setAndOr("or");
        oredCriteria.add(criteria);
        this.currentCriteria = criteria;
        return this;
    }

    public SimpleExample and() {
        SimpleExample.Criteria criteria = createCriteriaInternal();
        criteria.setAndOr("and");
        oredCriteria.add(criteria);
        this.currentCriteria = criteria;
        return this;
    }

    protected SimpleExample.Criteria createCriteriaInternal() {
        SimpleExample.Criteria criteria = new SimpleExample.Criteria(exists, notNull);
        return criteria;
    }

    protected SimpleExample.Criteria createCriteria() {
        SimpleExample.Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            criteria.setAndOr("and");
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public static class OrderBy {
        private SimpleExample example;
        private Boolean isProperty;

        public OrderBy(SimpleExample example) {
            this.example = example;
        }

        private String property(String property) {
            if (StringUtil.isEmpty(property) || StringUtil.isEmpty(property.trim())) {
                throw new MapperException("接收的property为空！");
            }
            property = property.trim();
            return property;
        }

        public SimpleExample.OrderBy orderBy(String property) {
            String column = property(property);
            if (column == null) {
                isProperty = false;
                return this;
            }
            if (StringUtil.isNotEmpty(example.getOrderByClause())) {
                example.setOrderByClause(example.getOrderByClause() + "," + column);
            } else {
                example.setOrderByClause(column);
            }
            isProperty = true;
            return this;
        }

        public SimpleExample.OrderBy desc() {
            if (isProperty) {
                example.setOrderByClause(example.getOrderByClause() + " DESC");
                isProperty = false;
            }
            return this;
        }

        public SimpleExample.OrderBy asc() {
            if (isProperty) {
                example.setOrderByClause(example.getOrderByClause() + " ASC");
                isProperty = false;
            }
            return this;
        }
    }

    protected abstract static class GeneratedCriteria {
        protected List<SimpleExample.Criterion> criteria;
        //字段是否必须存在
        protected boolean exists;
        //值是否不能为空
        protected boolean notNull;
        //连接条件
        protected String andOr;

        protected GeneratedCriteria(boolean exists, boolean notNull) {
            super();
            this.exists = exists;
            this.notNull = notNull;
            criteria = new ArrayList<SimpleExample.Criterion>();
        }


        private String column(String property) {
//        	return property;
            return StringUtils.camelToUnderline(property);
        }

        private String property(String property) {
            return property;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new MapperException("Value for condition cannot be null");
            }
            if (condition.startsWith("null")) {
                return;
            }
            criteria.add(new SimpleExample.Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                if (notNull) {
                    throw new MapperException("Value for " + property + " cannot be null");
                } else {
                    return;
                }
            }
            if (property == null) {
                return;
            }
            criteria.add(new SimpleExample.Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                if (notNull) {
                    throw new MapperException("Between values for " + property + " cannot be null");
                } else {
                    return;
                }
            }
            if (property == null) {
                return;
            }
            criteria.add(new SimpleExample.Criterion(condition, value1, value2));
        }

        protected void addOrCriterion(String condition) {
            if (condition == null) {
                throw new MapperException("Value for condition cannot be null");
            }
            if (condition.startsWith("null")) {
                return;
            }
            criteria.add(new SimpleExample.Criterion(condition, true));
        }

        protected void addOrCriterion(String condition, Object value, String property) {
            if (value == null) {
                if (notNull) {
                    throw new MapperException("Value for " + property + " cannot be null");
                } else {
                    return;
                }
            }
            if (property == null) {
                return;
            }
            criteria.add(new SimpleExample.Criterion(condition, value, true));
        }

        protected void addOrCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                if (notNull) {
                    throw new MapperException("Between values for " + property + " cannot be null");
                } else {
                    return;
                }
            }
            if (property == null) {
                return;
            }
            criteria.add(new SimpleExample.Criterion(condition, value1, value2, true));
        }

        public SimpleExample.Criteria andIsNull(String property) {
            addCriterion(column(property) + " is null");
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andIsNotNull(String property) {
            addCriterion(column(property) + " is not null");
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andEqualTo(String property, Object value) {
            addCriterion(column(property) + " =", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andNotEqualTo(String property, Object value) {
            addCriterion(column(property) + " <>", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andGreaterThan(String property, Object value) {
            addCriterion(column(property) + " >", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andGreaterThanOrEqualTo(String property, Object value) {
            addCriterion(column(property) + " >=", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andLessThan(String property, Object value) {
            addCriterion(column(property) + " <", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andLessThanOrEqualTo(String property, Object value) {
            addCriterion(column(property) + " <=", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andIn(String property, Iterable values) {
            addCriterion(column(property) + " in", values, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andNotIn(String property, Iterable values) {
            addCriterion(column(property) + " not in", values, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andBetween(String property, Object value1, Object value2) {
            addCriterion(column(property) + " between", value1, value2, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andNotBetween(String property, Object value1, Object value2) {
            addCriterion(column(property) + " not between", value1, value2, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andLike(String property, String value) {
            addCriterion(column(property) + "  like", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria andNotLike(String property, String value) {
            addCriterion(column(property) + "  not like", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        /**
         * 手写条件
         *
         * @param condition 例如 "length(countryname)<5"
         * @return
         */
        public SimpleExample.Criteria andCondition(String condition) {
            addCriterion(condition);
            return (SimpleExample.Criteria) this;
        }

        /**
         * 手写左边条件，右边用value值
         *
         * @param condition 例如 "length(countryname)="
         * @param value     例如 5
         * @return
         */
        public SimpleExample.Criteria andCondition(String condition, Object value) {
            criteria.add(new SimpleExample.Criterion(condition, value));
            return (SimpleExample.Criteria) this;
        }

        /**
         * 将此对象的不为空的字段参数作为相等查询条件
         *
         * @param param 参数对象
         * @author Bob {@link}0haizhu0@gmail.com
         * @Date 2015年7月17日 下午12:48:08
         */
        public SimpleExample.Criteria andEqualTo(Object param) {
            MetaObject metaObject = MetaObjectUtil.forObject(param);
            String[] properties = metaObject.getGetterNames();
            for (String property : properties) {
                //属性和列对应Map中有此属性
                Object value = metaObject.getValue(property);
                //属性值不为空
                if (value != null) {
                    andEqualTo(property, value);
                }
            }
            return (SimpleExample.Criteria) this;
        }

        /**
         * 将此对象的所有字段参数作为相等查询条件，如果字段为 null，则为 is null
         *
         * @param param 参数对象
         */
        public SimpleExample.Criteria andAllEqualTo(Object param) {
            MetaObject metaObject = MetaObjectUtil.forObject(param);
            String[] properties = metaObject.getGetterNames();
            for (String property : properties) {
                //属性和列对应Map中有此属性
                Object value = metaObject.getValue(property);
                //属性值不为空
                if (value != null) {
                    andEqualTo(property, value);
                } else {
                    andIsNull(property);
                }
            }
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orIsNull(String property) {
            addOrCriterion(column(property) + " is null");
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orIsNotNull(String property) {
            addOrCriterion(column(property) + " is not null");
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orEqualTo(String property, Object value) {
            addOrCriterion(column(property) + " =", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orNotEqualTo(String property, Object value) {
            addOrCriterion(column(property) + " <>", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orGreaterThan(String property, Object value) {
            addOrCriterion(column(property) + " >", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orGreaterThanOrEqualTo(String property, Object value) {
            addOrCriterion(column(property) + " >=", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orLessThan(String property, Object value) {
            addOrCriterion(column(property) + " <", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orLessThanOrEqualTo(String property, Object value) {
            addOrCriterion(column(property) + " <=", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orIn(String property, Iterable values) {
            addOrCriterion(column(property) + " in", values, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orNotIn(String property, Iterable values) {
            addOrCriterion(column(property) + " not in", values, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orBetween(String property, Object value1, Object value2) {
            addOrCriterion(column(property) + " between", value1, value2, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orNotBetween(String property, Object value1, Object value2) {
            addOrCriterion(column(property) + " not between", value1, value2, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orLike(String property, String value) {
            addOrCriterion(column(property) + "  like", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        public SimpleExample.Criteria orNotLike(String property, String value) {
            addOrCriterion(column(property) + "  not like", value, property(property));
            return (SimpleExample.Criteria) this;
        }

        /**
         * 手写条件
         *
         * @param condition 例如 "length(countryname)<5"
         * @return
         */
        public SimpleExample.Criteria orCondition(String condition) {
            addOrCriterion(condition);
            return (SimpleExample.Criteria) this;
        }

        /**
         * 手写左边条件，右边用value值
         *
         * @param condition 例如 "length(countryname)="
         * @param value     例如 5
         * @return
         */
        public SimpleExample.Criteria orCondition(String condition, Object value) {
            criteria.add(new SimpleExample.Criterion(condition, value, true));
            return (SimpleExample.Criteria) this;
        }

        /**
         * 将此对象的不为空的字段参数作为相等查询条件
         *
         * @param param 参数对象
         * @author Bob {@link}0haizhu0@gmail.com
         * @Date 2015年7月17日 下午12:48:08
         */
        public SimpleExample.Criteria orEqualTo(Object param) {
            MetaObject metaObject = MetaObjectUtil.forObject(param);
            String[] properties = metaObject.getGetterNames();
            for (String property : properties) {
                //属性和列对应Map中有此属性
                Object value = metaObject.getValue(property);
                //属性值不为空
                if (value != null) {
                    orEqualTo(property, value);
                }
            }
            return (SimpleExample.Criteria) this;
        }

        /**
         * 将此对象的所有字段参数作为相等查询条件，如果字段为 null，则为 is null
         *
         * @param param 参数对象
         */
        public SimpleExample.Criteria orAllEqualTo(Object param) {
            MetaObject metaObject = MetaObjectUtil.forObject(param);
            String[] properties = metaObject.getGetterNames();
            for (String property : properties) {
                //属性和列对应Map中有此属性
                Object value = metaObject.getValue(property);
                //属性值不为空
                if (value != null) {
                    orEqualTo(property, value);
                } else {
                    orIsNull(property);
                }
            }
            return (SimpleExample.Criteria) this;
        }

        public List<SimpleExample.Criterion> getAllCriteria() {
            return criteria;
        }

        public String getAndOr() {
            return andOr;
        }

        public void setAndOr(String andOr) {
            this.andOr = andOr;
        }

        public List<SimpleExample.Criterion> getCriteria() {
            return criteria;
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }
    }

    public static class Criteria extends SimpleExample.GeneratedCriteria {

    	protected Criteria() {
            super(true, true);
        }

        protected Criteria(boolean exists, boolean notNull) {
            super(exists, notNull);
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private String andOr;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        protected Criterion(String condition) {
            this(condition, false);
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            this(condition, value, typeHandler, false);
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null, false);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            this(condition, value, secondValue, typeHandler, false);
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null, false);
        }

        protected Criterion(String condition, boolean isOr) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
            this.andOr = isOr ? "or" : "and";
        }

        protected Criterion(String condition, Object value, String typeHandler, boolean isOr) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            this.andOr = isOr ? "or" : "and";
            if (value instanceof Collection<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, boolean isOr) {
            this(condition, value, null, isOr);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler, boolean isOr) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
            this.andOr = isOr ? "or" : "and";
        }

        protected Criterion(String condition, Object value, Object secondValue, boolean isOr) {
            this(condition, value, secondValue, null, isOr);
        }

        public String getAndOr() {
            return andOr;
        }

        public void setAndOr(String andOr) {
            this.andOr = andOr;
        }

        public String getCondition() {
            return condition;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        public Object getValue() {
            return value;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }
    }

    public static class Builder {
        protected EntityTable table;
        private StringBuilder orderByClause;
        private boolean distinct;
        private boolean exists;
        private boolean notNull;
        private boolean forUpdate;
        //查询字段
        private Set<String> selectColumns;
        private List<Sqls.Criteria> sqlsCriteria;
        //动态表名
        private List<SimpleExample.Criteria> exampleCriterias;

        public Builder() {
            this(true);
        }

        public Builder(boolean exists) {
            this(exists, false);
        }

        public Builder(boolean exists, boolean notNull) {
            this.exists = exists;
            this.notNull = notNull;
            this.orderByClause = new StringBuilder();
            this.sqlsCriteria = new ArrayList<Sqls.Criteria>(2);
        }

        public SimpleExample.Builder distinct() {
            return setDistinct(true);
        }

        public SimpleExample.Builder forUpdate() {
            return setForUpdate(true);
        }

        public SimpleExample.Builder selectDistinct(String... properties) {
            select(properties);
            this.distinct = true;
            return this;
        }

        public SimpleExample.Builder select(String... properties) {
            if (properties != null && properties.length > 0) {
                if (this.selectColumns == null) {
                    this.selectColumns = new LinkedHashSet<String>();
                }
                for (String property : properties) {
                    this.selectColumns.add(property);
                }
            }
            return this;
        }

        public SimpleExample.Builder where(Sqls sqls) {
            Sqls.Criteria criteria = sqls.getCriteria();
            criteria.setAndOr("and");
            this.sqlsCriteria.add(criteria);
            return this;
        }

        public SimpleExample.Builder where(SqlsCriteria sqls) {
            Sqls.Criteria criteria = sqls.getCriteria();
            criteria.setAndOr("and");
            this.sqlsCriteria.add(criteria);
            return this;
        }

        public SimpleExample.Builder andWhere(Sqls sqls) {
            Sqls.Criteria criteria = sqls.getCriteria();
            criteria.setAndOr("and");
            this.sqlsCriteria.add(criteria);
            return this;
        }

        public SimpleExample.Builder andWhere(SqlsCriteria sqls) {
            Sqls.Criteria criteria = sqls.getCriteria();
            criteria.setAndOr("and");
            this.sqlsCriteria.add(criteria);
            return this;
        }

        public SimpleExample.Builder orWhere(Sqls sqls) {
            Sqls.Criteria criteria = sqls.getCriteria();
            criteria.setAndOr("or");
            this.sqlsCriteria.add(criteria);
            return this;
        }

        public SimpleExample.Builder orWhere(SqlsCriteria sqls) {
            Sqls.Criteria criteria = sqls.getCriteria();
            criteria.setAndOr("or");
            this.sqlsCriteria.add(criteria);
            return this;
        }

        public SimpleExample.Builder orderBy(String... properties) {
            return orderByAsc(properties);
        }

        public SimpleExample.Builder orderByAsc(String... properties) {
            contactOrderByClause(" Asc", properties);
            return this;
        }

        public SimpleExample.Builder orderByDesc(String... properties) {
            contactOrderByClause(" Desc", properties);
            return this;
        }

        private void contactOrderByClause(String order, String... properties) {
            StringBuilder columns = new StringBuilder();
            for (String property : properties) {
                String column;
                if ((column = propertyforOderBy(property)) != null) {
                    columns.append(",").append(column);
                }
            }
            columns.append(order);
            if (columns.length() > 0) {
                orderByClause.append(columns);
            }
        }

        public SimpleExample build() {
            this.exampleCriterias = new ArrayList<SimpleExample.Criteria>();
            for (Sqls.Criteria criteria : sqlsCriteria) {
                SimpleExample.Criteria exampleCriteria = new SimpleExample.Criteria(this.exists, this.notNull);
                exampleCriteria.setAndOr(criteria.getAndOr());
                for (Sqls.Criterion criterion : criteria.getCriterions()) {
                    String condition = criterion.getCondition();
                    String andOr = criterion.getAndOr();
                    String property = criterion.getProperty();
                    Object[] values = criterion.getValues();
                    transformCriterion(exampleCriteria, condition, property, values, andOr);
                }
                exampleCriterias.add(exampleCriteria);
            }

            if (this.orderByClause.length() > 0) {
                this.orderByClause = new StringBuilder(this.orderByClause.substring(1, this.orderByClause.length()));
            }

            return new SimpleExample(this);
        }

        private void transformCriterion(SimpleExample.Criteria exampleCriteria, String condition, String property, Object[] values, String andOr) {
            if (values.length == 0) {
                if ("and".equals(andOr)) {
                    exampleCriteria.addCriterion(column(property) + " " + condition);
                } else {
                    exampleCriteria.addOrCriterion(column(property) + " " + condition);
                }
            } else if (values.length == 1) {
                if ("and".equals(andOr)) {
                    exampleCriteria.addCriterion(column(property) + " " + condition, values[0], property(property));
                } else {
                    exampleCriteria.addOrCriterion(column(property) + " " + condition, values[0], property(property));
                }
            } else if (values.length == 2) {
                if ("and".equals(andOr)) {
                    exampleCriteria.addCriterion(column(property) + " " + condition, values[0], values[1], property(property));
                } else {
                    exampleCriteria.addOrCriterion(column(property) + " " + condition, values[0], values[1], property(property));
                }
            }
        }

        private String column(String property) {
//        	return property;
            return StringUtils.camelToUnderline(property);
        }

        private String property(String property) {
            return property;
        }

        private String propertyforOderBy(String property) {
            if (StringUtil.isEmpty(property) || StringUtil.isEmpty(property.trim())) {
                throw new MapperException("接收的property为空！");
            }
            property = property.trim();
            return column(property);
        }

        public SimpleExample.Builder setDistinct(boolean distinct) {
            this.distinct = distinct;
            return this;
        }

        public SimpleExample.Builder setForUpdate(boolean forUpdate) {
            this.forUpdate = forUpdate;
            return this;
        }

    }

    public String getCountColumn() {
        return countColumn;
    }

    @Override
    public String getDynamicTableName() {
        return null;
    }


    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public List<SimpleExample.Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public Set<String> getSelectColumns() {
        return selectColumns;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }

    /**
     * 指定 count(property) 查询属性
     *
     * @param property
     */
    public void setCountProperty(String property) {
        this.countColumn = property;
    }
    
    public String authfilter;

	public String getAuthfilter() {
		return authfilter;
	}

	public void setAuthfilter(String authfilter) {
		this.authfilter = authfilter;
	}
    
    
}

