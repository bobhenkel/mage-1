/*
 * Copyright 2012 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.cards.CardArea;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.view.CardsView;
import org.mage.card.arcane.CardPanel;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PickPileDialog extends MageDialog {

    private final CardArea pile1;
    private final CardArea pile2;

    private boolean pickedPile1 = false;

    /**
     * Create the frame.
     */
    public PickPileDialog() {
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.WEST);
        panel.setLayout(new BorderLayout(0, 0));

        pile1 = new CardArea();
        panel.add(pile1, BorderLayout.CENTER);

        JButton btnChoosePile1 = new JButton("Pile 1");
        btnChoosePile1.addActionListener(e -> btnPile1ActionPerformed(e));
        panel.add(btnChoosePile1, BorderLayout.NORTH);

        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.EAST);
        panel_1.setLayout(new BorderLayout(0, 0));

        pile2 = new CardArea();
        panel_1.add(pile2, BorderLayout.CENTER);

        JButton btnChoosePile2 = new JButton("Pile 2");
        btnChoosePile2.addActionListener(e -> btnPile2ActionPerformed(e));
        panel_1.add(btnChoosePile2, BorderLayout.NORTH);
    }

    public void cleanUp() {
        for (Component comp : pile1.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                pile1.remove(comp);
            }
        }
        for (Component comp : pile2.getComponents()) {
            if (comp instanceof CardPanel) {
                ((CardPanel) comp).cleanUp();
                pile2.remove(comp);
            }
        }
    }

    public void loadCards(String name, CardsView pile1, CardsView pile2, BigCard bigCard, UUID gameId) {
        this.title = name;
        this.pile1.loadCardsNarrow(pile1, bigCard, gameId);
        this.pile2.loadCardsNarrow(pile2, bigCard, gameId);

        if (getParent() != MageFrame.getDesktop() /*|| this.isClosed*/) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        }
        pack();

        Point centered = SettingsManager.instance.getComponentPosition(getWidth(), getHeight());
        this.setLocation(centered.x, centered.y);
        GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, this);

        this.revalidate();
        this.repaint();
        this.setModal(true);

        // window settings
        if (this.isModal()){
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        }else{
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }

        this.setVisible(true);
    }

    private void btnPile1ActionPerformed(java.awt.event.ActionEvent evt) {
        pickedPile1 = true;
        this.hideDialog();
    }

    private void btnPile2ActionPerformed(java.awt.event.ActionEvent evt) {
        pickedPile1 = false;
        this.hideDialog();
    }

    public boolean isPickedPile1() {
        return this.pickedPile1;
    }
}
