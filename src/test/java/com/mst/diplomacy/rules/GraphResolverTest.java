package com.mst.diplomacy.rules;


import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphResolverTest {

    @Test
    public void testEmpty() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(Collections.emptySet(), Collections.emptySet(), Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testSingleHold() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(Sets.newHashSet(new Hold("a")), Collections.emptySet(), Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testSingleMove() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(Collections.emptySet(), Sets.newHashSet(new Move("a", "b")), Collections.emptySet());

        assertTrue(result.getDislodged().isEmpty());
        assertEquals(Sets.newHashSet(new Move("a", "b")), result.getSuccessfulMoves());
    }

    @Test
    public void testSingleMoveBlockedByHold() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(Sets.newHashSet(new Hold("b")), Sets.newHashSet(new Move("a", "b")), Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testStandoffMove() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Collections.emptySet(),
                Sets.newHashSet(new Move("a", "b"), new Move("c", "b")),
                Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testStandoffDoesNotDislodge() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(new Hold("b")),
                Sets.newHashSet(new Move("a", "b"), new Move("c", "b")),
                Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testFailedMoveFailsSubsequent() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(new Hold("c")),
                Sets.newHashSet(new Move("a", "b"), new Move("b", "c")),
                Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testFailedBouncedMoveFailsSubsequent() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Collections.emptySet(),
                Sets.newHashSet(new Move("a", "b"), new Move("b", "c"), new Move("d", "c")),
                Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testBug() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Collections.emptySet(),
                Sets.newHashSet(new Move("GoL", "Mar"), new Move("Mar", "Spa"), new Move("Spa", "Gas"), new Move("Bre", "Gas")),
                Collections.emptySet());

        assertNothingHappened(result);
    }

    @Test
    public void testSubsequentSuccess() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(),
                Sets.newHashSet(new Move("a", "b"), new Move("b", "c"), new Move("c", "a")),
                Collections.emptySet());

        assertTrue(result.getDislodged().isEmpty());
        assertEquals(Sets.newHashSet(new Move("a", "b"), new Move("b", "c"), new Move("c", "a")),
                result.getSuccessfulMoves());
    }

    @Test
    public void testDirectPlaceSwitchFails() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Collections.emptySet(),
                Sets.newHashSet(new Move("a", "b"), new Move("b", "a")),
                Collections.emptySet());

        assertNothingHappened(result);
    }

    /********************************************
     * SUPPORT
     */

    @Test
    public void testSupportMoveThatDoesNotHappen() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Collections.emptySet(),
                Sets.newHashSet(),
                Sets.newHashSet(new Support("a", "b", "c")));

        assertNothingHappened(result);
    }

    @Test
    public void testSupportMoveUndisputed() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Collections.emptySet(),
                Sets.newHashSet(new Move("a", "b")),
                Sets.newHashSet(new Support("c", "a", "b")));

        assertTrue(result.getDislodged().isEmpty());
        assertEquals(Sets.newHashSet(new Move("a", "b")), result.getSuccessfulMoves());
    }

    @Test
    public void testSupportMoveAgainstHold() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(new Hold("b")),
                Sets.newHashSet(new Move("a", "b")),
                Sets.newHashSet(new Support("c", "a", "b")));

        assertEquals(Sets.newHashSet(new Move("a", "b")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("b"), result.getDislodged());
    }

    @Test
    public void testD12DislodgedUnitCausesStandOff() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(),
                Sets.newHashSet(new Move("boh", "mun"), new Move("mun", "sil"), new Move("war", "sil")),
                Sets.newHashSet(
                        new Support("tyr", "boh", "mun"),
                        new Support("ber", "mun", "sil"),
                        new Support("pru", "war", "sil")));

        assertEquals(Sets.newHashSet(new Move("pru", "war")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("war"), result.getDislodged());
    }
    
    @Test
    public void testD13DislodgedUnitHasNoEffectOnDislodgingProvince() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(),
                Sets.newHashSet(new Move("bul", "rum"), new Move("rum", "bul"), new Move("sev", "rum")),
                Sets.newHashSet(
                        new Support("ser", "rum", "bul")));

        assertEquals(Sets.newHashSet(new Move("rum", "bul"), new Move("sev", "bul")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("bul"), result.getDislodged());
    }

    @Test
    public void testD14AnotherDislodgedExample() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(),
                Sets.newHashSet(new Move("bul", "rum"), new Move("rum", "bul"), new Move("sev", "rum")),
                Sets.newHashSet(
                        new Support("bla", "bul", "rum"),
                        new Support("gre", "rum", "bul"),
                        new Support("ser", "rum", "bul")));

        assertEquals(Sets.newHashSet(new Move("rum", "bul"), new Move("sev", "bul")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("bul"), result.getDislodged());
    }

    @Test
    public void testD15SupportCutByAttack() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(new Hold("war")),
                Sets.newHashSet(new Move("pru", "war"), new Move("boh", "sil")),
                Sets.newHashSet(new Support("sil", "pru", "war")));

        assertNothingHappened(result);
    }

    @Test
    public void testD16SupportNotCutByDirectAttack() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(),
                Sets.newHashSet(new Move("pru", "war"), new Move("war", "sil")),
                Sets.newHashSet(new Support("sil", "pru", "war")));

        assertEquals(Sets.newHashSet(new Move("pru", "war")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("war"), result.getDislodged());
    }

    @Test
    public void testD17SupportCutByDirectAttackIfLodged() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(),
                Sets.newHashSet(new Move("ber", "pru"), new Move("pru", "sil"), new Move("bal", "pru")),
                Sets.newHashSet(
                        new Support("sil", "ber", "pru"),
                        new Support("war", "pru", "sil")));

        assertEquals(Sets.newHashSet(new Move("boh", "mun")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("mun"), result.getDislodged());
    }

    @Test
    public void testD18SupportNotCutByIndirectAttackIfLodged() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(new Hold("ber")),
                Sets.newHashSet(new Move("mun", "sil"), new Move("pru", "ber"), new Move("boh", "mun")),
                Sets.newHashSet(
                        new Support("sil", "pru", "ber"),
                        new Support("tyr", "boh", "mun")));

        assertEquals(Sets.newHashSet(new Move("boh", "mun")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("mun"), result.getDislodged());
    }

    @Test
    public void testD22SelfDislodgement() {
        GraphResolver subject = createResolver();

        Result result = subject.resolve(
                Sets.newHashSet(new Hold("ber")),
                Sets.newHashSet(new Move("mun", "sil"), new Move("pru", "ber"), new Move("boh", "mun")),
                Sets.newHashSet(
                        new Support("sil", "pru", "ber"),
                        new Support("tyr", "boh", "mun")));

        assertEquals(Sets.newHashSet(new Move("boh", "mun")), result.getSuccessfulMoves());
        assertEquals(Sets.newHashSet("mun"), result.getDislodged());
    }

    private void assertNothingHappened(Result result) {
        assertEquals(Collections.emptySet(), result.getDislodged());
        assertEquals(Collections.emptySet(), result.getSuccessfulMoves());
    }

    private GraphResolver createResolver() {
        return new GraphResolver();
    }

}