package com.example.web.model;

import com.example.core.model.TimeSheetEntryModel;

import java.time.LocalDateTime;

public class TimeSheetSlotModel {
    private boolean isBusy;
    private int slot;
    private LocalDateTime from;
    private LocalDateTime to;
    private TimeSheetEntryModel entry;

    public boolean isBusy() {
        return isBusy;
    }

    public int getSlot() {
        return slot;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public TimeSheetEntryModel getEntry() {
        return entry;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "TimeSheetSlotModel{" +
                "slot=" + slot +
                ", from=" + from +
                ", to=" + to +
                ", model=" + entry +
                '}';
    }

    public static final class Builder {
        final TimeSheetSlotModel slot;

        Builder() {
            slot = new TimeSheetSlotModel();
        }

        public Builder withSlot(int slotNumber) {
            slot.slot = slotNumber;
            return this;
        }

        public Builder fromTime(LocalDateTime from) {
            slot.from = from;
            return this;
        }

        public Builder toTime(LocalDateTime to) {
            slot.to = to;
            return this;
        }

        public Builder withEntry(TimeSheetEntryModel model) {
            slot.entry = model;
            return this;
        }

        public TimeSheetSlotModel build() {
            return slot;
        }

    }
}
