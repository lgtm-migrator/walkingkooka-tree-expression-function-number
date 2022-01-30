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

import org.junit.jupiter.api.Test;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.ExpressionNumber;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionContext;
import walkingkooka.tree.expression.function.FakeExpressionFunctionContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class NumberExpressionFunctionTestCase<F extends ExpressionFunction<ExpressionNumber, ExpressionFunctionContext>> extends ExpressionFunctionTestCase<F, ExpressionNumber> {

    final static ExpressionNumberKind KIND = ExpressionNumberKind.DEFAULT;

    NumberExpressionFunctionTestCase() {
        super();
    }

    @Test
    public final void testDoesntConvert() {
        if (!(this instanceof NumberExpressionFunctionQuotientTest ||
                this instanceof NumberExpressionFunctionRandomTest ||
                this instanceof NumberExpressionFunctionRandomBetweenTest ||
                this instanceof NumberExpressionFunctionCountTest ||
                this instanceof NumberExpressionFunctionToTest)) {
            assertThrows(
                    ClassCastException.class,
                    () -> {
                        this.createBiFunction()
                                .apply(Lists.of(1), this.createContext());
                    }
            );
        }
    }

    @Override
    public final void applyAndCheck2(final F function,
                                     final List<Object> parameters,
                                     final ExpressionNumber result) {
        this.applyAndCheck2(
                function,
                parameters.stream()
                        .map(i -> KIND.create((Number) i))
                        .collect(Collectors.toList()),
                this.createContext(),
                result
        );
    }

    @Override
    public final ExpressionFunctionContext createContext() {
        return new FakeExpressionFunctionContext() {
            @Override
            public ExpressionNumberKind expressionNumberKind() {
                return KIND;
            }

            @Override
            public <TT> Either<TT, String> convert(final Object value,
                                                   final Class<TT> target) {

                if (!(NumberExpressionFunctionTestCase.this instanceof NumberExpressionFunctionToTest)) {
                    throw new UnsupportedOperationException();
                }

                try {
                    final Number number = value instanceof String ?
                            new BigDecimal((String) value) :
                            (Number) value;
                    return Either.left(target.cast(KIND.create(number)));
                } catch (final Exception fail) {
                    return this.failConversion(value, target);
                }
            }
        };
    }

    @Override
    public final String typeNamePrefix() {
        return NumberExpressionFunction.class.getSimpleName();
    }
}
