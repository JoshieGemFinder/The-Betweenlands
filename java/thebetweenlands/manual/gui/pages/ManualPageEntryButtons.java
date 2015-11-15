package thebetweenlands.manual.gui.pages;

import thebetweenlands.manual.gui.GuiManualBase;
import thebetweenlands.manual.gui.entries.ManualEntry;
import thebetweenlands.manual.gui.entries.ManualEntryItem;
import thebetweenlands.manual.gui.widgets.ButtonWidget;
import thebetweenlands.manual.gui.widgets.ManualWidgetsBase;

import java.util.ArrayList;

/**
 * Created by Bart on 6-11-2015.
 */
public class ManualPageEntryButtons extends ManualPage {

    public ManualPageEntryButtons(GuiManualBase manual, ArrayList<ManualEntryItem> entries) {
        int height = 0;
        for (ManualEntryItem entryItem : entries) {
            widgets.add(new ButtonWidget(manual, 15, 10 + height, entryItem.items, entryItem));
            height += 18;
        }
    }


    public ManualPageEntryButtons(GuiManualBase manual, ArrayList<ManualEntry> entries, String resourceLocation, int indexStart) {
        int height = 0;
        int times = 0;
        for (ManualEntry entryItem : entries) {
            indexStart++;
            widgets.add(new ButtonWidget(manual, 15, 10 + height, resourceLocation, entryItem, indexStart + times));
            height += 18;
            times++;
        }
    }
}
