/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.expression.function.number;

import walkingkooka.tree.expression.function.ExpressionFunctionContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Base for any function that expects a single number.
 */
abstract class NumberNumberExpressionFunction extends NumberExpressionFunction<Number> {

    /**
     * Package private ctor
     */
    NumberNumberExpressionFunction() {
        super();
    }

    @Override
    public final Number apply(final List<Object> parameters,
                              final ExpressionFunctionContext context) {
        this.checkParameterCount(parameters, 1);

        return NumberExpressionFunctionExpressionNumberVisitor.apply(this.number(parameters, 0, context),
                this,
                context);
    }

    abstract Number applyBigDecimal(final BigDecimal number);

    abstract Number applyBigInteger(final BigInteger number);

    abstract Number applyDouble(final Double number);

    abstract Number applyLong(final Long number);
}
