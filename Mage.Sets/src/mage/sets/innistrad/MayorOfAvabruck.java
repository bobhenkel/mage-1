/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.innistrad;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;

/**
 *
 * @author North, noxx
 */
public class MayorOfAvabruck extends CardImpl<MayorOfAvabruck> {

    private static final String ruleText = "Other Human creatures you control get +1/+1";

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human creatures");

    static {
        filter.add(new SubtypePredicate("Human"));
    }

    public MayorOfAvabruck(UUID ownerId) {
        super(ownerId, 193, "Mayor of Avabruck", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.subtype.add("Werewolf");

        this.canTransform = true;
        this.secondSideCard = new HowlpackAlpha(ownerId);

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Other Human creatures you control get +1/+1.
        Effect effect = new ConditionalContinousEffect(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true), new InvertCondition(new TransformedCondition()), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mayor of Avabruck.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.getInstance(), TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public MayorOfAvabruck(final MayorOfAvabruck card) {
        super(card);
    }

    @Override
    public MayorOfAvabruck copy() {
        return new MayorOfAvabruck(this);
    }
}
