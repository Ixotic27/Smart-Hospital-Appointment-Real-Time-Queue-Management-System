package com.hospital.services;

import com.hospital.models.Appointment;
import java.util.List;

public class QueueUpdater implements Runnable {

    private QueueService queueService;
    private int doctorId;
    private boolean running = true; // control thread

    public QueueUpdater(QueueService queueService, int doctorId) {
        this.queueService = queueService;
        this.doctorId = doctorId;
    }

    // method to stop thread
    public void stop() {
        running = false;
    }

    @Override
    public void run() {

        while (running) {

            try {
                Thread.sleep(5000);            // 5 sec delay

                List<Appointment> queue = queueService.getLiveQueue(doctorId);

                System.out.println("\n--- Live Queue Update ---");

                if (queue == null || queue.size() == 0) {
                    System.out.println("No patients in queue");
                } else {
                    for (int i = 0; i < queue.size(); i++) {
                        Appointment app = queue.get(i);
                        System.out.println("Position " + (i + 1) + " -> " + app);
                    }
                }

            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}