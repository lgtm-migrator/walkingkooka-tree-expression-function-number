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

import walkingkooka.Cast;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionContext;

import java.util.List;

/**
 * A {@link ExpressionFunction} that performs some operation and returns a {@link ExpressionNumber}.
 */
final class NumberExpressionFunctionTo<C extends ExpressionFunctionContext> extends NumberExpressionFunction<C> {

    /**
     * Instance getter.
     */
    static <C extends ExpressionFunctionContext> NumberExpressionFunctionTo<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private static final NumberExpressionFunctionTo<?> INSTANCE = new NumberExpressionFunctionTo<>();

    /**
     * Private ctor
     */
    private NumberExpressionFunctionTo() {
        super("number");
    }

    @Override
    public ExpressionNumber apply(final List<Object> parameters, final C context) {
        this.checkOnlyRequiredParameters(parameters);

        return context.convertOrFail(
                NUMBER.getOrFail(parameters, 0),
                ExpressionNumber.class
        );
    }
}
