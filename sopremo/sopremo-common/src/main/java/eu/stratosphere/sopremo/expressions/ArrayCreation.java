package eu.stratosphere.sopremo.expressions;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import eu.stratosphere.sopremo.EvaluationContext;
import eu.stratosphere.sopremo.JsonUtil;
import eu.stratosphere.sopremo.jsondatamodel.ArrayNode;
import eu.stratosphere.sopremo.jsondatamodel.JsonNode;

/**
 * Creates an array of the given expressions.
 * 
 * @author Arvid Heise
 */
@OptimizerHints(scope = Scope.ANY)
public class ArrayCreation extends ContainerExpression {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1681947333740209285L;

	private final SopremoExpression<EvaluationContext>[] elements;

	/**
	 * Initializes ArrayCreation to create an array of the given expressions.
	 * 
	 * @param elements
	 *        the expressions that evaluate to the elements in the array
	 */
	public ArrayCreation(final EvaluationExpression... elements) {
		this.elements = elements;
	}

	/**
	 * Initializes ArrayCreation to create an array of the given expressions.
	 * 
	 * @param elements
	 *        the expressions that evaluate to the elements in the array
	 */
	public ArrayCreation(final List<EvaluationExpression> elements) {
		this.elements = elements.toArray(new EvaluationExpression[elements.size()]);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		return Arrays.equals(this.elements, ((ArrayCreation) obj).elements);
	}

	@Override
	public JsonNode evaluate(final JsonNode node, final EvaluationContext context) {
		final ArrayNode arrayNode = JsonUtil.NODE_FACTORY.arrayNode();
		for (final SopremoExpression<EvaluationContext> expression : this.elements)
			arrayNode.add(expression.evaluate(node, context));
		return arrayNode;
	}

	@Override
	public int hashCode() {
		return 53 + Arrays.hashCode(this.elements);
	}

	@Override
	public Iterator<SopremoExpression<EvaluationContext>> iterator() {
		return Arrays.asList(this.elements).iterator();
	}

	@Override
	protected void toString(final StringBuilder builder) {
		builder.append(Arrays.toString(this.elements));
	}
}