import styles from './EachTime.module.css';

export default function EachTime({ eachTimes, setEachTimes }) {

    return (
        <>
            {
                eachTimes.map((eachTime, index) => (
                    <div key={index} className={styles.eachTime}>
                        <span>{eachTime.start} ~ {eachTime.end}</span>
                        <button className={styles.deleteButtonStyle}
                                onClick={() => {
                                    eachTime.index = index;
                                    setEachTimes(eachTimes.filter(eachTime => {
                                        return eachTime.index !== index; 
                                    }));
                                }}
                        >
                            X
                        </button>
                    </div>
                ))
            }
        </>
    );
}