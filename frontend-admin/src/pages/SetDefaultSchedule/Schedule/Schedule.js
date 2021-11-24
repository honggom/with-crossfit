import styles from './Schedule.module.css';
import React, { useState } from 'react';
import ReadScheduleModal from '../../../component/ReadScheduleModal/ReadScheduleModal';
import { getEachTimeByScheduleId } from '../../../api/pages/SetDefaultSchedule/Schedule/Schedule';
import { useNavigate } from 'react-router-dom';
import { errorHandle } from '../../../util/util';

export default function Schedule({ schedules, setSchedules }) {

    let navigate = useNavigate();

    const [isOpen, setIsOpen] = useState(false);
    const [eachTimes, setEachTimes] = useState([]);
    const [name, setName] = useState('');
    const [scheduleId, setScheduleId] = useState(null);

    if (schedules !== null && schedules !== undefined) {
        return (
            <>
                {
                    schedules.map((schedule, index) => (
                        <tr key={schedule.id}>
                            <td className={styles.td} colSpan={2}>
                                <span onClick={() => {
                                    getEachTimeByScheduleId(schedule.id).then((response) => {
                                        setEachTimes(response.data);
                                    }).catch((error) => {
                                        errorHandle(error, navigate);
                                    });
                                    setScheduleId(schedule.id);
                                    setName(schedule.name);
                                    setIsOpen(true);
                                }}>
                                    {schedule.name}
                                </span>
                            </td>
                        </tr>
                    ))
                }
                <ReadScheduleModal isOpen={isOpen} 
                                   setIsOpen={setIsOpen} 
                                   name={name} 
                                   eachTimes={eachTimes} 
                                   scheduleId={scheduleId}
                                   setSchedules={setSchedules}
                />
            </>
        );
    } else {
        return null;
    }
}