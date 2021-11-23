import styles from './ManageSchedule.module.css';
import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from "react-router-dom";
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import koLocale from '@fullcalendar/core/locales/ko';
import { getSpecificSchedule } from '../../api/pages/ManageSchedule';
import { errorHandle } from '../../util/util';

// 이벤트 클리하면 일정 / 일정 변경 버튼 모달 활성화
export default function ManageSchedule() {

    let navigate = useNavigate();

    useEffect(() => {
    }, [])

    return (
        <div className={styles.wrapper}>

            <div className={styles.titleWrapper}>
                <span>일정 관리</span>
            </div>

            <div className={styles.middleWrapper}>
                <Link to='/set-default-schedule'>
                <button className={styles.defaultScheduleButton}>기본 일정 설정</button>
                </Link>
            </div>

            <div className={styles.calWrapper}>
                <FullCalendar
                    locales={[koLocale]}
                    plugins={[dayGridPlugin, interactionPlugin]}
                    initialView="dayGridMonth"
                    dateClick={(agrs) => {
                        // console.log(agrs);
                    }}
                    events={[
                        { title: 'event 1', date: '2021-11-12' },
                        { title: 'event 2', date: '2021-11-13' }
                    ]}
                    datesSet={(info) => {
                        getSpecificSchedule(info.startStr, info.endStr).then((respnose) => {
                            console.log(respnose.data);
                        }).catch((error) => {
                            console.log(error);
                        });
                    }}
                    eventClick={(info) => {
                        // console.log(info.event);
                        // console.log(info.view);
                    }}
                />
            </div>
        </div>
    );
}