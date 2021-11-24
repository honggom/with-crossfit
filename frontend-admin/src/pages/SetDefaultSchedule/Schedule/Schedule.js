import styles from './Schedule.module.css';

export default function Schedule({ schedules, setSchedules }) {
    if (schedules !== null && schedules !== undefined) {
        return (
            <>
                {
                    schedules.map((schedule, index) => (
                        <tr key={schedule.id}>
                            <td className={styles.td} colSpan={2}><span>{schedule.name}</span></td>
                        </tr>
                    ))
                }
            </>
        );
    } else {
        return null;
    }
}