import styles from './ManageSchedule.module.css';
import React, { useState, useEffect } from 'react';

import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from "@fullcalendar/interaction";

export default function ManageSchedule() {

    return (
        <div className={styles.wrapper}>    

            <div className={styles.titleWrapper}>
                <span>일정 관리</span>
            </div>

            <div className={styles.calWrapper}>
                <FullCalendar
                    plugins={[dayGridPlugin, interactionPlugin]}
                    initialView="dayGridMonth"
                    dateClick={(agrs) => {
                        console.log(agrs);
                    }}
                />
            </div>

        </div>
    );
}